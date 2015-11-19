package guru.springframework.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by xinghuangxu on 11/18/15.
 */
@Entity
public class Project {

    public enum Type {
        Gerrit,
        Reviewboard
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String name;

    private Date lastModifiedDate;

    private Type type;

    private String url;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    private Set<Review> reviews;


    public Integer getId() {
        return this.id;
    }

    public Project setLastModifiedDate(Date date) {
        lastModifiedDate = date;
        return this;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Project setType(Type type) {
        this.type = type;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Project setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public Project setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return this.name;
    }

}