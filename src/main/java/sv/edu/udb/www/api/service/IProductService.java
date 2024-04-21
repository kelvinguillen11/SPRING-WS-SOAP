package sv.edu.udb.www.api.service;

import sv.edu.udb.www.api.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(int id);
    boolean addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
}
