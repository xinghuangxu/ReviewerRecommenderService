package guru.springframework.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinghuangxu on 11/20/15.
 */
public class Change implements Serializable {

    private String projectName;
    private List<String> filePaths= new ArrayList<>();

    public String getProjectName(){
        return projectName;
    }

    public List<String> getFilePaths(){
        return filePaths;
    }

    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public void setFilePaths(List<String> filePaths){
        this.filePaths = filePaths;
    }

    public void addFilePath(String filePath){
        filePaths.add(filePath);
    }
}
