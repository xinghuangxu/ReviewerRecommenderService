package guru.springframework.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xinghuangxu on 11/18/15.
 */
@Entity
public class Review implements Serializable {

    @Id
    private String reviewId;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "review_reviewer", joinColumns = {
            @JoinColumn(name = "Review_ID", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "Reviewer_ID",
                    nullable = false, updatable = false)})
    private Set<Reviewer> reviewers = new HashSet<>(0);

    public String getReviewId() {
        return reviewId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review", orphanRemoval = true)
    private List<FilePath> filePaths;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review", orphanRemoval = true)
    private List<Comment> comments;


    public Set<Reviewer> getReviewers() {
        return this.reviewers;
    }

    public void setReviewers(Set<Reviewer> reviewers) {
        this.reviewers = reviewers;
    }

    public Review setReviewId(String reviewId) {
        this.reviewId = reviewId;
        return this;
    }

    public Review setProject(Project project) {
        this.project = project;
        return this;
    }

    public void addReviewer(Reviewer reviewer) {
        reviewers.add(reviewer);
    }
}
