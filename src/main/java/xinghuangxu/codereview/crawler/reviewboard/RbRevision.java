package xinghuangxu.codereview.crawler.reviewboard;

import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbRevision {
    private JSONObject jsonObject;
    private JSONObject links;

    public RbRevision(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        links = jsonObject.getJSONObject("links");
    }

    public RbFileList getFiles(){
        JSONObject filsLink = links.getJSONObject("files");
        String filesUrl = filsLink.getString("href");
        return new RbFileList(filesUrl);
    }
}
