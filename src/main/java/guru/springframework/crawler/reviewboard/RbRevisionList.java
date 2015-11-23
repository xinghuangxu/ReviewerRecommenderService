package guru.springframework.crawler.reviewboard;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbRevisionList {

    private final JSONObject jsonObject;
    private final JSONArray revisions;

    public RbRevisionList(String diffsUrl) {
        RbHttpRequest rbHttpRequest = new RbHttpRequest(diffsUrl);
        jsonObject = rbHttpRequest.getJsonObject();
        revisions = jsonObject.getJSONArray("diffs");
    }

    public int getLength() {
        return revisions.length();
    }

    public RbRevision getRevision(int index) {
        return new RbRevision((JSONObject) revisions.get(index));
    }
}
