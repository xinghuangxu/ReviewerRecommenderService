package guru.springframework.repositories;

import guru.springframework.configuration.RepositoryConfiguration;
import guru.springframework.domain.Project;
import guru.springframework.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void testDeleteProject() {
        Project mylynTask = new Project();
        mylynTask.setName("mylyn/org.eclipse.mylyn.tasks").setType(Project.Type.Gerrit).setUrl("https://git.eclipse.org/");
        projectRepository.save(mylynTask);

        Review review1 = new Review();
        review1.setReviewId("I6cf37ca92c014db141d0b1e674568474b435ed02");
        review1.setProject(mylynTask);
        reviewRepository.save(review1);

        projectRepository.delete(mylynTask.getId());

        Review expectedReview = reviewRepository.findOne(review1.getReviewId());
        assertNull(expectedReview);
    }
}
