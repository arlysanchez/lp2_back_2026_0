package pe.edu.upeu.backend_ventas.app.usecase;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.backend_ventas.domain.model.Product;
import pe.edu.upeu.backend_ventas.domain.port.in.ProductUseCase;
import pe.edu.upeu.backend_ventas.domain.port.on.FileStoragePort;
import pe.edu.upeu.backend_ventas.domain.port.on.ProductRepositoryPort;
import org.slf4j.*;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.controller.ProductController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductUseCaseImpl  implements ProductUseCase {
    private static final Logger log = LoggerFactory.getLogger(ProductUseCaseImpl.class);


    private final ProductRepositoryPort productRepositoryPort;
    private final FileStoragePort fileStoragePort;

    public ProductUseCaseImpl(ProductRepositoryPort productRepositoryPort, FileStoragePort fileStoragePort) {
        this.productRepositoryPort = productRepositoryPort;
        this.fileStoragePort = fileStoragePort;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepositoryPort.save(product);
    }
    @Override
    public Optional<Product> updateProduct(Long productId, Product product) {
        log.info(" Datos recibidos en updateProduct: id={}, body={}", productId, product.getName());
        return productRepositoryPort.fillById(productId).map( existingProduct -> {
            product.setId(productId);
            Product updatedProduct = productRepositoryPort.update(product);
            return updatedProduct;
        });
    }

    @Override
    public boolean deleteProduct(Long id) {
        return productRepositoryPort.fillById(id).map( existingProduct -> {
            //elimina la imagen de la carpeta
            if (existingProduct.getImageUrl() !=null && !existingProduct.getImageUrl().isEmpty()){
                fileStoragePort.delete(existingProduct.getImageUrl());
            }

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

    @Override
    @Transactional
    public Optional<Product> uploadImage(Long IdProduct, MultipartFile file) throws IOException {
        return productRepositoryPort.fillById(IdProduct)
                .map(prod ->{
                    //delete image ant
                    if(prod.getImageUrl()!=null && !prod.getImageUrl().isEmpty()){
                      fileStoragePort.delete(prod.getImageUrl());
                    }
                    //save the new image
                    String filename;
                    try {
                        filename = fileStoragePort.save(file);
                    }catch (IOException e){
                        throw new RuntimeException("Fallo al guardar la imagen",e);
                    }
                    //llamar al metodo del adapter
                    return productRepositoryPort.uploadImageUrl(IdProduct,filename)
                            .orElse(null);
                });
    }
}
