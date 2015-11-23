package guru.springframework.crawler;

import guru.springframework.crawler.gerrit.GerritCrawler;
import guru.springframework.crawler.reviewboard.RbCrawler;
import guru.springframework.domain.Project;
import guru.springframework.repositories.ProjectRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * Created by xinghuangxu on 11/18/15.
 */
@Component
public class Crawler {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private Logger log = Logger.getLogger(Crawler.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private GerritCrawler gerritCrawler;

    @Autowired
    private RbCrawler rbCrawler;

    public void updateKnowledgeBase() {
        log.info("Crawler is starting to crawl.");
        Iterable<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            if (project.getType() == Project.Type.Gerrit) {
                gerritCrawler.crawl(project);
            } else if (project.getType() == Project.Type.Reviewboard) {
                rbCrawler.crawl(project);
            }
            log.info("Crawling Project:" + project.getName());
        }
    }
}
