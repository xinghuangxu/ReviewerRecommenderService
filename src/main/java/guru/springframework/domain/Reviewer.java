package guru.springframework.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xinghuangxu on 11/19/15.
 */
@Entity
public class Reviewer {

    @Id
    private String reviewerId;

    private String name;

    private String email;

    private String username;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "reviewers")
    private Set<Review> reviews = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewer", orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>(0);

    public Set<Review> getReviews() {
        return this.reviews;
    }

    public Reviewer setReviewerId(String reviewerId){
        this.reviewerId = reviewerId;
        return this;
    }

    public Reviewer setName(String name){
        this.name = name;
        return this;
    }

    public Reviewer setEmail(String email){
        this.email = email;
        return this;
    }

    public Reviewer setUsername(String username){
        this.username = username;
        return this;
    }

}
