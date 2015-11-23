package guru.springframework.bootstrap;

import guru.springframework.crawler.reviewboard.RbFactory;
import guru.springframework.crawler.reviewboard.RbRepository;
import guru.springframework.domain.Project;
import guru.springframework.domain.Review;
import guru.springframework.repositories.ProjectRepository;
import guru.springframework.repositories.ReviewRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * Created by xinghuangxu on 11/18/15.
 */
@Component
public class ProjectLoader implements ApplicationListener<ContextRefreshedEvent> {
    private ProjectRepository projectRepository;

    private ReviewRepository reviewRepository;

    private Logger log = Logger.getLogger(ProjectLoader.class);

    @Autowired
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //Load Gerrit Sample Projects

//        Project mylynTask = new Project();
//        mylynTask.setName("mylyn/org.eclipse.mylyn.tasks").setType(Project.Type.Gerrit).setUrl("https://git.eclipse.org/");
//        projectRepository.save(mylynTask);
//
//        Review review1 = new Review();
//        review1.setReviewId("I6cf37ca92c014db141d0b1e674568474b435ed02");
//        review1.setProject(mylynTask);
//        reviewRepository.save(review1);
//
//        log.info("Saved MyLynTask - id: " + mylynTask.getId());
//
//        Project cdt = new Project();
//        cdt.setName("cdt/org.eclipse.cdt").setType(Project.Type.Gerrit).setUrl("https://git.eclipse.org/");
//        projectRepository.save(cdt);
//
//        log.info("Saved CDT - id:" + cdt.getId());

        //Load Rb Sample Projects
        String siteUrl = "https://reviews.apache.org/";
        String mesosProjectName = "mesos";
        Project mesos = new Project();
        mesos.setName(mesosProjectName).setType(Project.Type.Reviewboard).setUrl(siteUrl);
        RbFactory rbFactory = new RbFactory(siteUrl);
        RbRepository rbRepository = rbFactory.findRepositoryWithName(mesosProjectName);
        mesos.setExternalId(rbRepository.getId());
        projectRepository.save(mesos);
    }
}
