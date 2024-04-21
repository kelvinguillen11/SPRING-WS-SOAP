package sv.edu.udb.www.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sv.edu.udb.www.api.entity.Product;
import sv.edu.udb.www.api.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findByProductId(int id);
    List<Product> findByName(String name);
}
