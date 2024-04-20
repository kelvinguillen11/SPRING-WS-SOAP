package sv.edu.udb.www.api.repository;

import org.springframework.stereotype.Repository;
import sv.edu.udb.www.api.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
    ProductModel findByName(String name);
}
