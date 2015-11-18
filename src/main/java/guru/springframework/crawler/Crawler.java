package guru.springframework.crawler;

import guru.springframework.domain.Project;
import guru.springframework.repositories.ProjectRepository;
import guru.springframework.services.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xinghuangxu on 11/18/15.
 */
@Component
public class Crawler {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private Logger log = Logger.getLogger(Crawler.class);

    @Autowired
    private ProjectRepository projectRepository;

    public void updateKnowledgeBase(){
        log.info("Crawler is starting to crawl.");
        Iterable<Project> projects = projectRepository.findAll();
        
    }
}
