package guru.springframework.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by xinghuangxu on 11/18/15.
 */
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date lastModifiedDate;

    private String type;

    private String url;

    private String externalId;

    private String name;

    @OneToMany(mappedBy="project")
    private List<Review> reviews;


    public Integer getId(){
        return this.id;
    }

    public Project setLastModifiedDate(Date date){
        lastModifiedDate = date;
        return this;
    }

    public Project setType(String type){
        this.type = type;
        return this;
    }

    public Project setUrl(String url){
        this.url = url;
        return this;
    }

    public Project setExternalId(String externalId){
        this.externalId = externalId;
        return this;
    }

    public Project setName(String name){
        this.name = name;
        return this;
    }

}
