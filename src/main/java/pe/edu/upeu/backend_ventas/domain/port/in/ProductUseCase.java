package pe.edu.upeu.backend_ventas.domain.port.in;

import pe.edu.upeu.backend_ventas.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductUseCase {

    Product createProduct(Product product);
    Optional<Product> updateProduct(Long productId,Product product);
    boolean deleteProduct(Long id);
    List<Product> getAllProducts();
    Optional<Product> getAllProductById(Long id);
}
