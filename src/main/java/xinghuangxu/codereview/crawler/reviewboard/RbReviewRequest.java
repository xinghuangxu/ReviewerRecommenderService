package xinghuangxu.codereview.crawler.reviewboard;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbReviewRequest {

    private final JSONObject jsonObject;
    private final JSONObject links;
    private final String id;

    public RbReviewRequest(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        links = jsonObject.getJSONObject("links");
        JSONObject selfLink = links.getJSONObject("self");
        this.id = selfLink.getString("href");
    }

    public String getId() {
        return this.id;
    }

    public Date getLastUpdated() {
        try {
            return RbCrawler.Rb_DATE_FORMATTER.parse(jsonObject.getString("last_updated"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStatus() {
        return jsonObject.getString("status");
    }

    public RbCommentList getReviews() {
        JSONObject reviewsLink = links.getJSONObject("reviews");
        String reviewCommentsUrl = reviewsLink.getString("href");

        return new RbCommentList(reviewCommentsUrl);
    }

    public RbRevisionList getRevisions() {
        JSONObject diffsLink = links.getJSONObject("diffs");
        String diffsUrl = diffsLink.getString("href");
        return new RbRevisionList(diffsUrl);
    }
}
