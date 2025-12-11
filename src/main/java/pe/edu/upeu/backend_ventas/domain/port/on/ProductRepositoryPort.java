package pe.edu.upeu.backend_ventas.domain.port.on;

import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.backend_ventas.domain.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);
    Product update(Product product);
    void deleteProductById(Long id);
    List<Product> findAll();
    Optional<Product> fillById(Long id);

    Optional<Product> uploadImageUrl(Long IdProduct, String imageUrl) ;
}
