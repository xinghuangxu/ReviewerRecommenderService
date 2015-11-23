package guru.springframework.crawler.reviewboard;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbCommentList {

    private JSONObject jsonObject;
    private JSONArray comments;

    public RbCommentList(String reviewCommentsUrl) {
        RbHttpRequest rbHttpRequest = new RbHttpRequest(reviewCommentsUrl);
        jsonObject = rbHttpRequest.getJsonObject();
        comments = jsonObject.getJSONArray("reviews");
    }

    public int getLength(){
        return comments.length();
    }

    public RbComment getComment(int index){
        return new RbComment((JSONObject) comments.get(index));
    }
}
