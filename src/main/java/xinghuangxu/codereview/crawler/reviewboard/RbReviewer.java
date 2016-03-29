package xinghuangxu.codereview.crawler.reviewboard;

import xinghuangxu.codereview.crawler.JsonObjectWrapper;
import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbReviewer extends JsonObjectWrapper {

    private String id;

    public RbReviewer(JSONObject user) {
        this.id = user.getString("href");
    }

    public void getDetail() {
        RbHttpRequest rbHttpRequest = new RbHttpRequest(this.id);
        if(rbHttpRequest.getJsonObject().has("user")){
            jsonObject = rbHttpRequest.getJsonObject().getJSONObject("user");
        }else{
            System.out.print("User not found!");
        }
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return getString("fullname");
    }

    public String getEmail() {
        return getString("email");
    }

    public String getUsername() {
        return getString("username");
    }
}
