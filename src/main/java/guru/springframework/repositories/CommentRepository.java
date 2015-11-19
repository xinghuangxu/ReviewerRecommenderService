package guru.springframework.repositories;

import guru.springframework.domain.Comment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by xinghuangxu on 11/18/15.
 */
public interface CommentRepository extends CrudRepository<Comment, Integer> {
}
