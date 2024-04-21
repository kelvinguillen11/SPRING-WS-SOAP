package sv.edu.udb.www.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.edu.udb.www.api.entity.Product;
import sv.edu.udb.www.api.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {


    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        productRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Product getProductById(int id) {
        Product product = productRepository.findById(id);
        return product;
    }

    @Override
    public synchronized boolean addProduct(Product product) {
        List<Product> list = productRepository.findByName(product.getName());
        if(list.size() >0){
            return false;
        }
        else{
            product = productRepository.save(product);
            return true;
        }
    }

    @Override
    public void updateProduct(Product product) {
            productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

}
