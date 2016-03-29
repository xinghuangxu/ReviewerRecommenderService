package xinghuangxu.codereview.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xinghuangxu on 11/19/15.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length=1025)
    private String message;
    private Date date;

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public Comment() {

    }

    public Comment(String message, Date date, Review review, Reviewer reviewer) {
        this.message = message;
        this.date = date;
        this.review = review;
        this.reviewer = reviewer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    public Reviewer getReviewer(){
        return reviewer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
}
