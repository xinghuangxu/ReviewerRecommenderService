package guru.springframework.repositories;

import guru.springframework.domain.FilePath;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by xinghuangxu on 11/18/15.
 */
public interface FilePathRepository extends CrudRepository<FilePath, Integer> {
}
