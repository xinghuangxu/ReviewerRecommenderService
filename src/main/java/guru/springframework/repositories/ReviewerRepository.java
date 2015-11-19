package guru.springframework.repositories;

import guru.springframework.domain.Review;
import guru.springframework.domain.Reviewer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by xinghuangxu on 11/18/15.
 */
public interface ReviewerRepository extends CrudRepository<Reviewer, String> {
}
