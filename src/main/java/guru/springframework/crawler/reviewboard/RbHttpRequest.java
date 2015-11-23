package guru.springframework.crawler.reviewboard;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by xinghuangxu on 11/18/15.
 */
public class RbHttpRequest {
    //MEMBER VARIABLES
    public JSONArray reviewDataArray=new JSONArray();
    public JSONObject reviewDataObject=new JSONObject();
    //CONSTRUCTORS
    public RbHttpRequest(String url) throws JSONException
    {
        try {
//            System.out.println(url);
            //sending an httprequest with GET method and get result by httpEntity
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            HttpGet httpGet = new HttpGet(url);
            httpResponse = httpclient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            String response = EntityUtils.toString(httpEntity);
            reviewDataObject=new JSONObject(response);
//            System.out.println(response);
            //if response be empty the length of response is 8.
//            if(response.length()>8)
//            {
//                //JSON response has )]} extra characters which should be remove.
//                response=response.replace(")]}'","");
//                //response from this url:"https://git.eclipse.org/r/changes/?q=mylyn&o=ALL_REVISIONS&o=ALL_FILES&o=MESSAGES";
//                if(response.startsWith("\n["))
//                {
//                    //declare JSONArray from response
//                    reviewDataArray=new JSONArray(response);
////                    if(reviewDataArray.length()>1)
////                    {
////                        //declare JSONObject from reviewDataArray
////                        reviewDataObject=reviewDataArray.getJSONObject(1);
////                    }
////                    else
////                    {
////                        //declare JSONObject from reviewDataArray
////                        reviewDataObject=reviewDataArray.getJSONObject(0);
////                    }
//
//                }
//                //response from this url:""https://git.eclipse.org/r/changes/"+entry.getKey()+"/revisions/"+value+"/comments"
//                else if(response.startsWith("\n{"))
//                {
//                    reviewDataObject=new JSONObject(response);
//                    // System.out.println(response.toString());
//                }
//            }
        }

        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //GET Method
    public JSONArray getJsonArray()
    {
        return reviewDataArray;
    }

    //GET Method
    public JSONObject getJsonObject()
    {
        return reviewDataObject;
    }
}
