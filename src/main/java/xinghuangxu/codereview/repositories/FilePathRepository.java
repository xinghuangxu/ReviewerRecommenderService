package xinghuangxu.codereview.repositories;

import xinghuangxu.codereview.domain.FilePath;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by xinghuangxu on 11/18/15.
 */
public interface FilePathRepository extends CrudRepository<FilePath, Integer> {

    List<FilePath> findByName(String name);

    List<FilePath> findByNameEndingWith(String name);

}
