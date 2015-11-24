package guru.springframework.crawler;

import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class JsonObjectWrapper {

    protected JSONObject jsonObject;

    protected String getString(String name) {
        if (jsonObject != null && jsonObject.has(name)) {
            return jsonObject.getString(name);
        }
        return "";
    }
}
