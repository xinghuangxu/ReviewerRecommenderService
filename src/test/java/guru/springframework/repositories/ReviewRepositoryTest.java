package guru.springframework.repositories;

import guru.springframework.configuration.RepositoryConfiguration;
import guru.springframework.domain.FilePath;
import guru.springframework.domain.Review;
import guru.springframework.domain.Reviewer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private FilePathRepository filePathRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Test
    public void testDeleteReview() {

        Review review1 = new Review();
        review1.setReviewId("I6cf37ca92c014db141d0b1e674568474b435ed02");
        Set<Reviewer> reviewerSet = review1.getReviewers();
        Reviewer user1 = new Reviewer();
        user1.setReviewerId("user1").setName("leonx").setUsername("Xinghuang XU").setEmail("xinghuangxu@gmail.com");
        Reviewer user2 = new Reviewer();
        user2.setReviewerId("user2").setName("jordan").setUsername("Jordan Hsu").setEmail("jordan@gmail.com");
        reviewerSet.add(user1);
        reviewerSet.add(user2);
        reviewRepository.save(review1);

        Review expectedReview = reviewRepository.findOne(review1.getReviewId());
        assertNotNull(expectedReview);

        String[] filePaths = new String[]{"org.eclipse.mylyn.tasks.ui.tests/src/org/eclipse/mylyn/internal/tasks/ui/TaskListReviewArtifactListenerTest.java",
        "org.eclipse.mylyn.tasks.ui/src/org/eclipse/mylyn/internal/tasks/ui/TasksUiPlugin.java"};

        for(String filePath : filePaths){
            filePathRepository.save(new FilePath(filePath, review1));
        }

        Iterable<FilePath> expectedFilePaths = filePathRepository.findAll();

        for(FilePath fp : expectedFilePaths){
            assertTrue((filePaths[0] + filePaths[1]).contains(fp.getName()));
        }


//        review1 = reviewRepository.findOne(review1.getReviewId());
//
//        reviewRepository.save(review1);

        reviewRepository.delete(review1.getReviewId());
    }


}
