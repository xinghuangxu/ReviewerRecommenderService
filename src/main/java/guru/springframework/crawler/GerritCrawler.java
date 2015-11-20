package guru.springframework.crawler;

import guru.springframework.crawler.gerrit.GerritAccount;
import guru.springframework.crawler.gerrit.GerritReview;
import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by xinghuangxu on 11/18/15.
 */
@Component
public class GerritCrawler {

    private final int PAGE_SIZE = 1;
    private final int MAX_NUM = 5;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private FilePathRepository filePathRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private CommentRepository commentRepository;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public void crawl(Project project) {
        Long ageInMin = calculateAgeInMin(project);
        if (ageInMin != null && ageInMin < 1) {
            return; //has just been updated
        }
        project.setLastModifiedDate(new Date());
        projectRepository.save(project);

        int skipCount = 0;

        //start the crawling process
        int i = 0;
        GerritReview gerritReview;
        boolean halt = false;
        while (!halt && i < MAX_NUM) {
            skipCount += PAGE_SIZE;
            gerritReview = fetchGerritReivew(project, ageInMin, skipCount);
            halt = saveAsReview(project, gerritReview);
            i++;
        }
    }

    private boolean saveAsReview(Project project, GerritReview gerritReview) {
        Review review = reviewRepository.findOne(gerritReview.getId());
        Date updateDate;
        try {
            updateDate = formatter.parse(gerritReview.getDateUpdated());
        } catch (ParseException e) {
            e.printStackTrace();
            return !gerritReview.hasMoreChange();
        }
        if (review != null) { //delete if already exist
            if (review.getUpdateDate().compareTo(updateDate) == 0) {
                return true;
            }
            reviewRepository.delete(review.getReviewId());
        }
        review = new Review();
        review.setReviewId(gerritReview.getId());
        review.setUpdateDate(updateDate);
        review.setProject(project);
        review.setStatus(gerritReview.getStatus());


        //add reviewers to the db
        Map<String, String> reviewersMap = gerritReview.ReviewerAssignee();
        Set<String> reviewerIds = reviewersMap.keySet();
        for (String accountId : reviewerIds) {
            Reviewer reviewer = createReviewerIfNotExit(project, accountId);
            review.addReviewer(reviewer);
        }
        reviewRepository.save(review);

        List<List<String>> reviewMessages = gerritReview.reviewMessageExtractor();

        for (List<String> reviewComment : reviewMessages) {
            Date date = null;
            try {
                date = formatter.parse(reviewComment.get(2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Reviewer reviewer = createReviewerIfNotExit(project, reviewComment.get(1));
            Comment comment = new Comment(reviewComment.get(0), date, review, reviewer);
            commentRepository.save(comment);
        }

        //add file path
        List<String> fileVectors = gerritReview.reviewPatchFiles();
        for (String filePath : fileVectors) {
            filePathRepository.save(new FilePath(filePath, review));
        }
        return !gerritReview.hasMoreChange();
    }

    private Reviewer createReviewerIfNotExit(Project project, String accountId) {
        Reviewer reviewer = reviewerRepository.findOne(project.getUrl() + accountId);
        if (reviewer == null) {
            GerritAccount gerritAccount = getchGerritAccountInfo(project, accountId);
            reviewer = new Reviewer();
            reviewer.setReviewerId(project.getUrl() + accountId).setName(gerritAccount.getName())
                    .setEmail(gerritAccount.getEmail()).setUsername(gerritAccount.getUsername());
            reviewerRepository.save(reviewer);
        }
        return reviewer;
    }

    private GerritAccount getchGerritAccountInfo(Project project, String accountId) {
        String url = project.getUrl() + "/r/accounts/" + accountId;
        return new GerritAccount(url);
    }

    public GerritReview fetchGerritReivew(Project project, Long ageInMin, Integer skipCount) throws JSONException {    //String url="https://git.eclipse.org/r/changes/?q=mylyn&o=ALL_REVISIONS&o=ALL_FILES&o=MESSAGES&n=2&N="+skipCount+"\"";
        String url = getCrawlingUrl(project, ageInMin, skipCount);
        return new GerritReview(url);
    }


    public String getCrawlingUrl(Project project, Long ageInMin, Integer skipCount) {
        String ageFilter = "";
        if (ageInMin != null) {
            ageFilter = "+age:" + ageInMin + "m";
        }
        return project.getUrl() + "/r/changes/?q=project:" + project.getName() + ageFilter + "&o=DETAILED_LABELS&o=ALL_REVISIONS&o=ALL_FILES&o=MESSAGES&n=" + PAGE_SIZE + "&S=" + skipCount;
    }

    public Long calculateAgeInMin(Project project) {
        if (project.getLastModifiedDate() == null) return null;
        Date current = new Date();
        return (current.getTime() - project.getLastModifiedDate().getTime()) / (60 * 1000) % 60;
    }
}
