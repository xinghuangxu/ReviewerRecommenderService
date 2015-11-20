package guru.springframework.controllers;

import guru.springframework.domain.Change;
import guru.springframework.domain.Comment;
import guru.springframework.domain.FilePath;
import guru.springframework.domain.Review;
import guru.springframework.recommender.xfinder.RecommendedReviewer;
import guru.springframework.recommender.xfinder.ReviewTuple;
import guru.springframework.recommender.xfinder.XFinder;
import guru.springframework.repositories.FilePathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by xinghuangxu on 11/19/15.
 */
@RestController
public class RecommendController {

    @Autowired
    private FilePathRepository filePathRepository;

    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public @ResponseBody Change getChange(HttpServletRequest request) {
        String[] filePaths = {"org.eclipse.cdt.managedbuilder.core/pom.xml", "org.eclipse.cdt.managedbuilder.core/src/org/eclipse/cdt/managedbuilder/core/ITool.java"};

        Change change = new Change();
        change.setProjectName("org.eclipse.mylyn.task");
        change.setFilePaths(Arrays.asList(filePaths));
        return change;
    }

    @RequestMapping(value = "/recommend", method = RequestMethod.POST)
    public @ResponseBody List<RecommendedReviewer> saveRecommend(@RequestBody final Change change) {
        List<RecommendedReviewer> result = null;
        //fetch project with name

        //Create mPathReview Dict
        HashMap<String, ArrayList<ReviewTuple>> mPathReviewDict = new HashMap<>();
        List<FilePath> filePathList;
        for (String filePathName : change.getFilePaths()) {
            ArrayList<ReviewTuple> reviewTuples = new ArrayList();
            Set<String> reviewIds = new HashSet<>();
            filePathList = filePathRepository.findByNameEndingWith(filePathName);
            for (FilePath filePath : filePathList) {
                Review review = filePath.getReview();
                if (!reviewIds.contains(review.getReviewId())) {
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
        result = xFinder.run(change.getFilePaths(), new Date(), mPathReviewDict);
        return result;
    }
}
