package xinghuangxu.codereview.crawler.reviewboard;

import xinghuangxu.codereview.domain.Project;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbFactory {

    private String url;

    public RbFactory(String url) {
        this.url = url + "/api/";
    }

    public RbRepository findRepositoryWithName(String name) {
        //build the url
        String url = this.url + "repositories/" + "?name=" + name;
        RbHttpRequest crawlerHttpRequest = new RbHttpRequest(url);
        RbRepository rbRepository = new RbRepository((JSONObject) crawlerHttpRequest.getJsonObject().getJSONArray("repositories").get(0));
        rbRepository.getId();
        return rbRepository;
    }

    public RbReviewList findReviewsWithProject(Project project) {

        String url = this.url + "review-requests/" + "?status=all&repository=" + project.getExternalId();
        Date lastUpdated = project.getLastModifiedDate();
        if (lastUpdated != null) {
            String formattedLastUpdated = RbCrawler.Rb_DATE_FORMATTER.format(lastUpdated);
            url = url + "&last-updated-from=" + formattedLastUpdated;
        }
        return findReviewsWithUrl(url);
    }

    public RbReviewList findReviewsWithUrl(String url) {
        if (url == null) return null;
        RbHttpRequest crawlerHttpRequest = new RbHttpRequest(url);
        RbReviewList rbReviewList = new RbReviewList((JSONObject) crawlerHttpRequest.getJsonObject());
        return rbReviewList;
    }
}
