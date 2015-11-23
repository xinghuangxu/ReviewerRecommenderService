package guru.springframework.crawler.reviewboard;

import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbFactory {

    private String url;

    public RbFactory(String url) {
        this.url = url+"/api/";
    }

    public RbRepository findRepositoryWithName(String name) {
        //build the url
        String url = this.url + "repositories/" + "?name=" + name;
        RbHttpRequest crawlerHttpRequest = new RbHttpRequest(url);
        RbRepository rbRepository = new RbRepository((JSONObject)crawlerHttpRequest.getJsonObject().getJSONArray("repositories").get(0));
        rbRepository.getId();
        return rbRepository;
    }

    public RbReviewList findReviewsWithRepositoryId(String externalId) {
        String url = this.url + "review-requests/" + "?repository=" + externalId;
        return findReviewsWithUrl(url);
    }

    public RbReviewList findReviewsWithUrl(String url){
        if(url == null) return null;
        RbHttpRequest crawlerHttpRequest = new RbHttpRequest(url);
        RbReviewList rbReviewList = new RbReviewList((JSONObject)crawlerHttpRequest.getJsonObject());
        return rbReviewList;
    }
}
