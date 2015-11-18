package guru.springframework.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by xinghuangxu on 11/18/15.
 */
@Entity
public class Review implements Serializable{

    @Id
    private String externalId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="project_id")
    private Project project;

    public Review setExternalId(String externalId){
        this.externalId = externalId;
        return this;
    }

    public Review setProject(Project project){
        this.project = project;
        return this;
    }
}
