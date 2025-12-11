package pe.edu.upeu.backend_ventas.app.usecase;

import org.springframework.stereotype.Service;
import pe.edu.upeu.backend_ventas.domain.model.Product;
import pe.edu.upeu.backend_ventas.domain.port.in.ProductUseCase;
import pe.edu.upeu.backend_ventas.domain.port.on.ProductRepositoryPort;

import java.util.List;
import java.util.Optional;

@Service
public class ProductUseCaseImpl  implements ProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductUseCaseImpl(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }
    @Override
    public Product createProduct(Product product) {
        return productRepositoryPort.save(product);
    }
    @Override
    public Optional<Product> updateProduct(Long productId, Product product) {
        return productRepositoryPort.fillById(productId).map( existingProduct -> {
            product.setId(productId);
            Product updatedProduct = productRepositoryPort.update(product);
            return updatedProduct;
        });
    }

    @Override
    public boolean deleteProduct(Long id) {
        return productRepositoryPort.fillById(id).map( existingProduct -> {
            productRepositoryPort.deleteProductById(id);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepositoryPort.findAll();
    }

    @Override
    public Optional<Product> getAllProductById(Long id) {
        return productRepositoryPort.fillById(id);
    }
}
