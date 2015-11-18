package guru.springframework.repositories;

import guru.springframework.domain.Project;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by xinghuangxu on 11/18/15.
 */
public interface ProjectRepository extends CrudRepository<Project, Integer> {
}
