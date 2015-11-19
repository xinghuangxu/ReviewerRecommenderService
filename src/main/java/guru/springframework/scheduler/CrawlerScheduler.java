package guru.springframework.scheduler;

import guru.springframework.crawler.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by xinghuangxu on 11/18/15.
 */
@Component
public class CrawlerScheduler {

    @Autowired
    private Crawler crawler;

    @Scheduled(fixedRate = 50000)
    public void reportCurrentTime() {
        crawler.updateKnowledgeBase();
    }
}
