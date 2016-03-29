package xinghuangxu.codereview.repositories;

import xinghuangxu.codereview.domain.Review;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by xinghuangxu on 11/18/15.
 */
public interface ReviewRepository extends CrudRepository<Review, String> {
}
