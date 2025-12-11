package pe.edu.upeu.backend_ventas.infrastructure.adapter.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.backend_ventas.domain.model.Product;
import pe.edu.upeu.backend_ventas.domain.port.in.ProductUseCase;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.controller.dto.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
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

    private ProductDto.ProductResponse mapToProductResponse(Product product){
        if (product == null){
            return null;
        }
        return new ProductDto.ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

}
