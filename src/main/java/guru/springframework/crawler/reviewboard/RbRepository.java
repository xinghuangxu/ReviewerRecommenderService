package guru.springframework.crawler.reviewboard;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by xinghuangxu on 11/23/15.
 */
public class RbRepository {

    private String id;

    private JSONObject map;

    public RbRepository(JSONObject repositoryMap) {
        this.map = repositoryMap;
    }

    public String getId() {
        return map.getLong("id")+"";
    }

}
