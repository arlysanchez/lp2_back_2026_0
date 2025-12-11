package pe.edu.upeu.backend_ventas.domain.port.in;

import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.backend_ventas.domain.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductUseCase {

    Product createProduct(Product product);
    Optional<Product> updateProduct(Long productId,Product product);
    boolean deleteProduct(Long id);
    List<Product> getAllProducts();
    Optional<Product> getAllProductById(Long id);

    //metodo para subir imagen
    Optional<Product> uploadImage(Long IdProduct, MultipartFile file) throws IOException;
}
