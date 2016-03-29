package xinghuangxu.codereview.scheduler;

import xinghuangxu.codereview.crawler.Crawler;
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

    //500 seconds
    @Scheduled(fixedRate = 500000)
    public void reportCurrentTime() {
        crawler.updateKnowledgeBase();
    }
}
