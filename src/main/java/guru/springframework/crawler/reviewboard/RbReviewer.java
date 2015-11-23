package guru.springframework.crawler.reviewboard;

import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbReviewer {

    private String id;
    private JSONObject jsonObject;

    public RbReviewer(JSONObject user) {
        this.id = user.getString("href");
    }

    public void getDetail() {
        RbHttpRequest rbHttpRequest = new RbHttpRequest(this.id);
        jsonObject = rbHttpRequest.getJsonObject().getJSONObject("user");
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return jsonObject.getString("fullname");
    }

    public String getEmail() {
        return jsonObject.getString("email");
    }

    public String getUsername() {
        return jsonObject.getString("username");
    }
}
