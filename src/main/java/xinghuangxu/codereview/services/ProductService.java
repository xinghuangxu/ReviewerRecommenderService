package xinghuangxu.codereview.services;


import xinghuangxu.codereview.domain.Product;

public interface ProductService {
    Iterable<Product> listAllProducts();

    Product getProductById(Integer id);

    Product saveProduct(Product product);
}
