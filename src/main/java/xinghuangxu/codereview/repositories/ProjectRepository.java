package xinghuangxu.codereview.repositories;

import xinghuangxu.codereview.domain.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by xinghuangxu on 11/18/15.
 */
public interface ProjectRepository extends CrudRepository<Project, Integer> {
    List<Project> findByName(String name);

    List<Project> findByNameContaining(String partialName);
}
