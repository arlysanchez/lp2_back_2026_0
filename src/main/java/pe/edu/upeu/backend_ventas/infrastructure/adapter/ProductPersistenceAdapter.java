package pe.edu.upeu.backend_ventas.infrastructure.adapter;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import pe.edu.upeu.backend_ventas.domain.model.Product;
import pe.edu.upeu.backend_ventas.domain.port.on.ProductRepositoryPort;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.mapper.ProductMapper;
import pe.edu.upeu.backend_ventas.infrastructure.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

@Component
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductPersistenceAdapter(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        ProductEntity saveEntity = productRepository.save(productEntity);
        return productMapper.toDomainModel(saveEntity);
    }

    @Override
    @Transactional
    public Product update(Product product) {
        ProductEntity productEntity = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        productEntity.setStock(product.getStock());
        return productMapper.toDomainModel(productEntity);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);

    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productMapper.toDomainModelList(productEntities);
    }

    @Override
    public Optional<Product> fillById(Long id) {
        return productRepository.findById(id).map(productMapper::toDomainModel);
    }
}
