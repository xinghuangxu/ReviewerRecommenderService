package guru.springframework.controllers;

import guru.springframework.domain.Comment;
import guru.springframework.domain.FilePath;
import guru.springframework.domain.Review;
import guru.springframework.recommender.xfinder.ReviewTuple;
import guru.springframework.repositories.FilePathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by xinghuangxu on 11/19/15.
 */
@Controller
public class RecommendController {

    @Autowired
    private FilePathRepository filePathRepository;

    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public String getRecommend(HttpServletRequest request) {
        String[] filePaths = {"build/org.eclipse.cdt.managedbuilder.core/pom.xml", "build/org.eclipse.cdt.managedbuilder.core/src/org/eclipse/cdt/managedbuilder/core/ITool.java"};

        //Create mPathReview Dict
        HashMap<String, ArrayList<ReviewTuple>> mPathReviewDict;
        List<FilePath> filePathList;
        for (String filePathName : filePaths) {
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
        }
        return "Hello World!";
    }

    @RequestMapping(value = "/recommend", method = RequestMethod.POST)
    public String saveRecommend(HttpServletRequest request) {
        return "Hello World!";
    }
}
