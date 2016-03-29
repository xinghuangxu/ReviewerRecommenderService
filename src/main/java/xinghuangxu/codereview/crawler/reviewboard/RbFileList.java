package xinghuangxu.codereview.crawler.reviewboard;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbFileList {

    private final JSONObject jsonObject;
    private final JSONArray files;

    public RbFileList(String filesUrl) {
        RbHttpRequest rbHttpRequest = new RbHttpRequest(filesUrl);
        jsonObject = rbHttpRequest.getJsonObject();
        files = jsonObject.getJSONArray("files");
    }

    public int getLength(){
        return this.files.length();
    }

    public RbFile getFile(int index){
        return new RbFile((JSONObject) files.get(index));
    }
}
