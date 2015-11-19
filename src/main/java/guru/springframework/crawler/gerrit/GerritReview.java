package guru.springframework.crawler.gerrit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xinghuangxu on 11/18/15.
 */
public class GerritReview {

    //CONSTANTS
    //final private String url="https://git.eclipse.org/r/changes/?q=mylyn&o=ALL_REVISIONS&o=ALL_FILES&o=MESSAGES&n=1&N=002eaaa400006c6f";
    //MEMBER VARIABLES
    public JSONObject reviewDataObject = new JSONObject();
    public List<String> fileVector = new ArrayList();
    public List<List<String>> reviewMessageInfo = new ArrayList<List<String>>();
    public ArrayList<String> revisionList = new ArrayList<String>();
    public int patchsize = 0;
    public int NumberofPatches = 0;

    //CONSTRUCTOR

    public GerritReview(String url) throws JSONException {
        GerritHttpRequest obj = new GerritHttpRequest(url);
        reviewDataObject = obj.getjson();
    }

    //This Method returns an HashMap includes all of unique files which are reviewed in the patch set
    public List<String> reviewPatchFiles() throws JSONException {
        //keeps the list of revision numbers for extracting comments for each revision
        JSONObject revision = reviewDataObject.getJSONObject("revisions");
        Iterator<String> revisionkeys = revision.keys();

        while (revisionkeys.hasNext()) {
            String revsionkey = revisionkeys.next();
            int revisionnumber = revision.getJSONObject(revsionkey).getInt("_number");
            //System.out.println(revsionkey);
            if (revision.getJSONObject(revsionkey).has("files")) {
                JSONObject file = revision.getJSONObject(revsionkey).getJSONObject("files");
                Iterator<String> filekeys = file.keys();
                while (filekeys.hasNext()) {
                    fileVector.add(filekeys.next());

                }
            }
            NumberofPatches++;
        }
        return fileVector;
    }

    //This method returns the list of reviewer assignee for each review
    public HashMap<String, String> ReviewerAssignee() throws JSONException {
        HashMap<String, String> AssigneeMap = new HashMap<String, String>();
        //ArrayList<String> Assignee=new ArrayList<String>();

        if (reviewDataObject.getJSONObject("labels").has("Code-Review")) {
            if (reviewDataObject.getJSONObject("labels").getJSONObject("Code-Review").has("all")) {
                JSONArray CodeReview = reviewDataObject.getJSONObject("labels").getJSONObject("Code-Review").getJSONArray("all");
                String date;
                String name = "";
                for (int i = 0; i < CodeReview.length(); i++) {

                    if (CodeReview.getJSONObject(i).has("_account_id")) {
                        name = CodeReview.getJSONObject(i).getLong("_account_id")+"";
                    }
                    //TODO this part of code has been commented because for android project account_id has been used instead
                    //of name
					/*if (CodeReview.getJSONObject(i).has("_account_id"))
					{
						name = Long.toString(CodeReview.getJSONObject(i).getLong("_account_id"));
					}*/
                    if (CodeReview.getJSONObject(i).has("date")) {
                        date = CodeReview.getJSONObject(i).getString("date");
                    } else {
                        date = " ";
                    }
                    AssigneeMap.put(name, date);
                }
            }
        }
        return AssigneeMap;
    }


    //This method adds all the revision numbers for each review to the revisionList. we need revision list to access all the comments
    //available for each revision
    public void revisionKey() throws JSONException {
        JSONObject revision = reviewDataObject.getJSONObject("revisions");
        Iterator<String> revisionkeys = revision.keys();
        while (revisionkeys.hasNext()) {
            String revisionkey = revisionkeys.next();
            if (!revisionList.contains(revisionkey)) {
                revisionList.add(revisionkey);
            }
        }
    }


    //This Method returns a list of message, author and date which each reviewer wrote a comment in code review
    public List<List<String>> reviewMessageExtractor() throws JSONException {

        JSONArray messages = reviewDataObject.getJSONArray("messages");
        for (int j = 0; j < messages.length(); j++) {
            ArrayList<String> info = new ArrayList<String>();

            if ((messages.getJSONObject(j).has("message")) && (messages.getJSONObject(j).has("author")) && (messages.getJSONObject(j).has("date"))) {
                String message = messages.getJSONObject(j).getString("message").replaceAll("[\\r|\\n]", "");
                info.add(message);
                String author = "";

                if (messages.getJSONObject(j).getJSONObject("author").has("_account_id")) {
                    author = messages.getJSONObject(j).getJSONObject("author").getLong("_account_id")+"";
                }
                //TODO this part of code has been commented because for android project account_id has been used instead
                //of name
     			/*if(messages.getJSONObject(j).getJSONObject("author").has("_account_id"))
     			{
     				author=Long.toString(messages.getJSONObject(j).getJSONObject("author").getLong("_account_id"));
     			}*/
                info.add(author);
                String date = messages.getJSONObject(j).getString("date");
                info.add(date);
                reviewMessageInfo.add(info);
            }

            //String pattern="yyyy-mm-dd";
            //SimpleDateFormat format=new SimpleDateFormat(pattern);
            //Date daterightformat=format.parse(date);
            //System.out.println(message);
            //System.out.println(author);
            //System.out.println(daterightformat);

        }
        return reviewMessageInfo;
    }

    //return subject of codeReview
    public String getSubject() throws JSONException {
        String subject = "";
        if (reviewDataObject.has("subject")) {
            subject = reviewDataObject.getString("subject");

        }
        return subject;
    }

    //return owner of codeReview
    public String getOwner() throws JSONException {
        String owner = "";

        if (reviewDataObject.has("owner")) {
            if (reviewDataObject.getJSONObject("owner").has("name")) {
                owner = reviewDataObject.getJSONObject("owner").getString(
                        "name");
            }
        }
        //TODO this part of code has been commented because for android project account_id has been used instead
        //of name
		/*if(reviewDataObject.getJSONObject("owner").has("_account_id"))
		{
			owner=Long.toString(reviewDataObject.getJSONObject("owner").getLong("_account_id"));
		}*/
        return owner;
    }

    //return status of codeReview
    public String getStatus() throws JSONException {
        String status = reviewDataObject.getString("status");
        return status;
    }

    //return more_changes flag of codeReview
    public Boolean getMoreChange() throws JSONException {
        Boolean more_changes = reviewDataObject.getBoolean("_more_changes");
//        System.out.println(more_changes);
        return more_changes;
    }

    //return more_changes flag of codeReview
    public Boolean hasMoreChange() throws JSONException {
        Boolean more_changes = reviewDataObject.has("_more_changes");
        return more_changes;
    }

    public ArrayList<String> getRevision() {
        // TODO Auto-generated method stub
        return revisionList;
    }

    public String getId() throws JSONException {
        return reviewDataObject.getString("id");
    }

    public String getChangeId() throws JSONException {
        String ChangeId = reviewDataObject.getString("change_id");
        return ChangeId;
    }

    public String getNumber() throws JSONException {
        String number = Long.toString(reviewDataObject.getLong("_number"));
        //System.out.println(number);
        return number;
    }

    public String getCurrentRevisionkey() throws JSONException {
        String Revision = "0000";
        if (reviewDataObject.has("current_revision")) {
            Revision = reviewDataObject.getString("current_revision");
        }
        return Revision;
    }

    //return date which patch is created
    public String getDateCreated() throws JSONException {
        String CreatedDate = reviewDataObject.getString("created");
        return CreatedDate;
    }

    public String getpatchsize() {
        return Integer.toString(patchsize);
    }

    public String getproject() throws JSONException {
        String product = "";
        String project = reviewDataObject.getString("project");
        project = project.replaceAll("/", ".");
        String[] strsplit = project.split("\\.");
        //System.out.println(strsplit.length);
        for (int i = 0; i < strsplit.length; i++) {
            //System.out.println(strsplit[i]);
            if (!((strsplit[i].equalsIgnoreCase("org")) || (strsplit[i].equalsIgnoreCase("mylyn")) || (strsplit[i].equalsIgnoreCase("eclipse")))) {
                //System.out.println(strsplit[i]);
                product = strsplit[i] + "." + product;
            }
        }
        return product;
    }

    public String getNumberofPatches() {
        return Integer.toString(NumberofPatches);
    }

    public String getDateUpdated() throws JSONException {
        String UpdatedDate = "";
        if (reviewDataObject.has("updated")) {
            UpdatedDate = reviewDataObject.getString("updated");
        }
        return UpdatedDate;
    }
}
