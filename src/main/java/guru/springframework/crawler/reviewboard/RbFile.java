package guru.springframework.crawler.reviewboard;

import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbFile {
    private final JSONObject jsonObject;

    public String getDestFile(){
        return jsonObject.getString("dest_file");
    }

    public RbFile(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
