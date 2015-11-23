package guru.springframework.crawler.gerrit;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xinghuangxu on 11/18/15.
 */
public class GerritAccount {

    //CONSTANTS
    //final private String url="https://git.eclipse.org/r/changes/?q=mylyn&o=ALL_REVISIONS&o=ALL_FILES&o=MESSAGES&n=1&N=002eaaa400006c6f";
    //MEMBER VARIABLES
    private JSONObject reviewDataObject = new JSONObject();

    private String accountId;
    private String name;
    private String username;
    private String email;

    public String getAccountId(){
        return accountId;
    }

    public String getName(){
        return name;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    //CONSTRUCTOR
    public GerritAccount(String url) throws JSONException {
        GerritHttpRequest obj = new GerritHttpRequest(url);
        reviewDataObject = obj.getJsonObject();
        this.accountId = reviewDataObject.getLong("_account_id")+"";
        this.name = reviewDataObject.getString("name");
        this.username = reviewDataObject.getString("username");
        if(reviewDataObject.has("email")){
            this.email = reviewDataObject.getString("email");
        }
    }

}
