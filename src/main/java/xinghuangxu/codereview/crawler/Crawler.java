package xinghuangxu.codereview.crawler;

import xinghuangxu.codereview.crawler.gerrit.GerritCrawler;
import xinghuangxu.codereview.crawler.reviewboard.RbCrawler;
import xinghuangxu.codereview.domain.Project;
import xinghuangxu.codereview.repositories.ProjectRepository;
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
