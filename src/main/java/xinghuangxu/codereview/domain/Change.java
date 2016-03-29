package xinghuangxu.codereview.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinghuangxu on 11/20/15.
 */
public class Change implements Serializable {

    private Integer projectId;
    private String creationDate;
    private List<String> filePaths= new ArrayList<>();

    public Integer getProjectId(){
        return projectId;
    }

    public List<String> getFilePaths(){
        return filePaths;
    }

    public String getCreationDate(){return creationDate;}

    public void setFilePaths(List<String> filePaths){
        this.filePaths = filePaths;
    }

    public void setCreationDate(String creationDate){
        this.creationDate = creationDate;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
