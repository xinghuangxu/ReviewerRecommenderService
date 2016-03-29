package xinghuangxu.codereview.crawler.reviewboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xinghuangxu.codereview.domain.*;
import xinghuangxu.codereview.repositories.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Use the default page size when crawling reviews
 * Created by xinghuangxu on 11/18/15.
 */
@Component
public class RbCrawler {

    private final int MAX_NUM = 1000;

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

    //Reviewboard time pattern
    public static final SimpleDateFormat Rb_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public void crawl(Project project) {
        int i = 0;
        RbFactory rbFactory = new RbFactory(project.getUrl());
        RbReviewList rbReviewList = rbFactory.findReviewsWithProject(project);
        Date latestUpdatedDate = null;
        while (rbReviewList != null & i < MAX_NUM) {
            for (int j = 0; j < rbReviewList.getLength(); j++) {
                RbReviewRequest rbReviewRequest = rbReviewList.getReview(j);
                Date lastUpdated = saveAsReview(project, rbReviewRequest);
                if(latestUpdatedDate==null||latestUpdatedDate.before(lastUpdated)){
                    latestUpdatedDate = lastUpdated;
                }
            }
            rbReviewList = rbFactory.findReviewsWithUrl(rbReviewList.next());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }

        project.setLastModifiedDate(latestUpdatedDate);
        projectRepository.save(project);
    }

    private Date saveAsReview(Project project, RbReviewRequest rbReviewRequest) {
        String reviewId = project.getUrl() + rbReviewRequest.getId();
        Review review = reviewRepository.findOne(reviewId);
        Date lastUpdated = rbReviewRequest.getLastUpdated();
        if (review != null) { //delete if already exist
            if(review.getUpdateDate().compareTo(lastUpdated)==0){
                return lastUpdated;
            }
            reviewRepository.delete(review.getReviewId());
        }
        review = new Review();
        review.setReviewId(reviewId);
        review.setUpdateDate(lastUpdated);
        review.setProject(project);
        review.setStatus(rbReviewRequest.getStatus());
        reviewRepository.save(review);

        //add comments
        RbCommentList rbCommentList = rbReviewRequest.getReviews();
        for (int i = 0; i < rbCommentList.getLength(); i++) {
            RbComment rbComment = rbCommentList.getComment(i);
            RbReviewer rbReviewer = rbComment.getReviewer();
            Reviewer reviewer = createReviewerIfNotExit(rbReviewer);
            Date date = null;
            try {
                date = Rb_DATE_FORMATTER.parse(rbComment.getTimeStamp());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String message = rbComment.getBodyTop();
            Comment comment = new Comment(message.substring(0, Math.min(message.length(), 1000)), date, review, reviewer);
            commentRepository.save(comment);
        }

        RbRevisionList rbRevisionList = rbReviewRequest.getRevisions();
        Set<String> visitedFile = new HashSet<>();
        for (int i = 0; i < rbRevisionList.getLength(); i++) {
            RbRevision rbRevision = rbRevisionList.getRevision(i);
            RbFileList rbFileList = rbRevision.getFiles();
            for(int j=0;j<rbFileList.getLength();j++){
                RbFile rbFile = rbFileList.getFile(j);
                if(!visitedFile.contains(rbFile.getDestFile())){
                    visitedFile.add(rbFile.getDestFile());
                    filePathRepository.save(new FilePath(rbFile.getDestFile(), review));
                }
            }
        }
        return lastUpdated;
    }

    private Reviewer createReviewerIfNotExit(RbReviewer rbReviewer) {
        Reviewer reviewer = reviewerRepository.findOne(rbReviewer.getId());
        if (reviewer == null) {
            rbReviewer.getDetail();
            reviewer = new Reviewer();
            reviewer.setReviewerId(rbReviewer.getId()).setName(rbReviewer.getFullName())
                    .setEmail(rbReviewer.getEmail()).setUsername(rbReviewer.getUsername());
            reviewerRepository.save(reviewer);
        }
        return reviewer;
    }
}
