package guru.springframework.domain;

import javax.persistence.*;

/**
 * Created by xinghuangxu on 11/18/15.
 */
@Entity
public class FilePath {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    public String getName(){
        return name;
    }

    public FilePath(){

    }

    public FilePath(String name, Review review){
        this.name = name;
        this.review = review;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="review_id")
    private Review review;
}
