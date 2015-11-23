package guru.springframework.controllers;

import guru.springframework.domain.*;
import guru.springframework.recommender.xfinder.RecommendedReviewer;
import guru.springframework.recommender.xfinder.ReviewTuple;
import guru.springframework.recommender.xfinder.XFinder;
import guru.springframework.repositories.FilePathRepository;
import guru.springframework.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xinghuangxu on 11/19/15.
 */
@RestController
public class RecommendController {

    @Autowired
    private FilePathRepository filePathRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public
    @ResponseBody
    Change getChange(HttpServletRequest request) {
        String[] filePaths = {"org.eclipse.cdt.managedbuilder.core/pom.xml", "org.eclipse.cdt.managedbuilder.core/src/org/eclipse/cdt/managedbuilder/core/ITool.java"};

        Change change = new Change();
        change.setProjectId(1);
        change.setFilePaths(Arrays.asList(filePaths));
        return change;
    }

    @RequestMapping(value = "/recommend", method = RequestMethod.POST)
    public
    @ResponseBody
    List<RecommendedReviewer> saveRecommend(@RequestBody final Change change) throws ParseException {
        List<RecommendedReviewer> result = null;
        Date creationDate = new Date();
        if (change.getCreationDate() != null) {
            creationDate = formatter.parse(change.getCreationDate());
        }
        //fetch project with name
        Project project = projectRepository.findOne(change.getProjectId());
        if (project == null) return null;
        //Create mPathReview Dict
        HashMap<String, ArrayList<ReviewTuple>> mPathReviewDict = new HashMap<>();
        List<FilePath> filePathList;
        for (String filePathName : change.getFilePaths()) {
            ArrayList<ReviewTuple> reviewTuples = new ArrayList();
            Set<String> reviewIds = new HashSet<>();
            filePathList = filePathRepository.findByNameEndingWith(filePathName);
            for (FilePath filePath : filePathList) {
                Review review = filePath.getReview();
                if (review.getProject() == project && !reviewIds.contains(review.getReviewId())) {
                    reviewIds.add(review.getReviewId());
                    //get all the comments of that review
                    List<Comment> comments = review.getComments();
                    for (Comment comment : comments) {
                        reviewTuples.add(new ReviewTuple(review.getReviewId(), comment.getReviewer(), comment.getDate()));
                    }
                }
            }
            mPathReviewDict.put(filePathName, reviewTuples);
        }
        XFinder xFinder = new XFinder();
        result = xFinder.run(change.getFilePaths(), creationDate, mPathReviewDict);
        return result;
    }
}
