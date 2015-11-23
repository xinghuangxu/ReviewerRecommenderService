package guru.springframework.crawler.reviewboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/20/15.
 */
public class RbReviewList {

    private JSONObject jsonObject;
    private JSONArray reviewRequestsJsonArray;

    public RbReviewList(JSONObject jsonObject) throws JSONException {
        this.jsonObject = jsonObject;
        reviewRequestsJsonArray = jsonObject.getJSONArray("review_requests");
    }

    public int getLength(){
        return reviewRequestsJsonArray.length();
    }

    public RbReviewRequest getReview(int index){
        return new RbReviewRequest((JSONObject)reviewRequestsJsonArray.get(index));
    }

    public String next(){
        JSONObject links = jsonObject.getJSONObject("links");
        JSONObject next = links.getJSONObject("next");
        if(next == null){
            return null;
        }
        return next.getString("href");
    }
}
