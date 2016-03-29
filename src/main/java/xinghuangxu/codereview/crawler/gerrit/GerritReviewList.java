package xinghuangxu.codereview.crawler.gerrit;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by xinghuangxu on 11/20/15.
 */
public class GerritReviewList {

    public JSONArray reviewDataArray;

    public GerritReviewList(String url) throws JSONException {
        GerritHttpRequest obj = new GerritHttpRequest(url);
        reviewDataArray = obj.getJsonArray();
    }

    public int length() {
        return reviewDataArray.length();
    }

    public GerritReview get(int index) {
        return new GerritReview(reviewDataArray.getJSONObject(index));
    }
}
