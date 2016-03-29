package xinghuangxu.codereview.crawler.reviewboard;

import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbComment {
    private final JSONObject links;
    private final JSONObject jsonObject;

    public RbComment(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.links = jsonObject.getJSONObject("links");
    }

    public RbReviewer getReviewer() {
        return new RbReviewer(links.getJSONObject("user"));
    }

    public String getTimeStamp(){
        return jsonObject.getString("timestamp");
    }

    public String getBodyTop(){
        return jsonObject.getString("body_top");
    }
}
