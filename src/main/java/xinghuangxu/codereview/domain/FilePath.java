package xinghuangxu.codereview.domain;

import javax.persistence.*;

/**
 * Created by xinghuangxu on 11/18/15.
 */
@Entity
@Table(indexes = {
        @Index(columnList = "name", name = "filepath_name_index"),
})
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

    public Review getReview() {
        return review;
    }
}
