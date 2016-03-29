package xinghuangxu.codereview.repositories;

import xinghuangxu.codereview.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer>{
}
