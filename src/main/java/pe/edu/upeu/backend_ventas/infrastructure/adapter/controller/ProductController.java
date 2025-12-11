package pe.edu.upeu.backend_ventas.infrastructure.adapter.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.backend_ventas.domain.model.Product;
import pe.edu.upeu.backend_ventas.domain.port.in.ProductUseCase;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.controller.dto.ProductDto;
import org.slf4j.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductUseCase productUseCase;
    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto.ProductResponse>> getAllProducts(){
        List<ProductDto.ProductResponse> products = productUseCase.getAllProducts()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
               return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ProductDto.ProductResponse>> getAllProductsById(@PathVariable Long id){
        List<ProductDto.ProductResponse> products = productUseCase.getAllProductById(id)
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductDto.ProductResponse> createProduct(@RequestBody ProductDto.ProductRequest productRequest ){
        //mapear el DTO al modelo
        Product productCreate = new Product();
        productCreate.setName(productRequest.name());
        productCreate.setDescription(productRequest.description());
        productCreate.setPrice(productRequest.price());
        productCreate.setStock(productRequest.stock());

        Product createdProduct = productUseCase.createProduct(productCreate);
        return new ResponseEntity<>(mapToProductResponse(createdProduct), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto.ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductDto.ProductRequest productRequest ){
        //mapear el DTO al modelo
        log.info(" Datos recibidos en updateProduct: id={}, body={}", id, productRequest);
        Product productEdit = new Product();
        productEdit.setId(id);
        productEdit.setName(productRequest.name());
        productEdit.setDescription(productRequest.description());
        productEdit.setPrice(productRequest.price());
        productEdit.setStock(productRequest.stock());

            return productUseCase.updateProduct(id,productEdit)
                .map(prod ->ResponseEntity.ok(mapToProductResponse(prod)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        if (productUseCase.deleteProduct(id)){
            return ResponseEntity.noContent().build(); //HTTP 204
        }else{
            return ResponseEntity.notFound().build(); //HTTP 404
        }
    }

    @PostMapping("/upload-image/{id}")
    public ResponseEntity<ProductDto.ProductResponse> uploadImage
            (@PathVariable Long id, @RequestParam("file")MultipartFile file){
        try {
            return productUseCase.uploadImage(id,file)
                    .map(prod -> ResponseEntity.ok(mapToProductResponse(prod)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IOException e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ProductDto.ProductResponse mapToProductResponse(Product product){
        if (product == null){
            return null;
        }
        String imageUrl = null;
        if (product.getImageUrl()!=null && !product.getImageUrl().isEmpty()){
            imageUrl ="http://localhost:8082/images/"+product.getImageUrl();
        }
        return new ProductDto.ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                imageUrl
        );
    }

}
