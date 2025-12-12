package pe.edu.upeu.backend_ventas.infrastructure.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.backend_ventas.domain.model.DetailSale;
import pe.edu.upeu.backend_ventas.domain.model.Product;
import pe.edu.upeu.backend_ventas.domain.model.Sale;
import pe.edu.upeu.backend_ventas.domain.port.in.SaleUseCase;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.SalePersistenceAdapter;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.SaleRepository;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.controller.dto.SaleDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/sales")
public class SaleController {

    private final SaleUseCase saleUseCase;

    public SaleController(SaleUseCase saleUseCase) {
        this.saleUseCase = saleUseCase;
    }
    @PostMapping
    public ResponseEntity<SaleDto.SaleResponse> createSale(@RequestBody SaleDto.SaleRequest request){
        Sale saleDomain = toDomain(request);
        Sale createdSale = saleUseCase.createSale(saleDomain);
        return new ResponseEntity<>(toResponse(createdSale), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SaleDto.SaleResponse>> getAllSales(){
        List<SaleDto.SaleResponse> response = saleUseCase.getAllSales().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SaleDto.SaleResponse> getSaleById(@PathVariable Long id){
        return saleUseCase.getAllSaleById(id)
                .map(sale-> ResponseEntity.ok(toResponse(sale)))
                .orElse(ResponseEntity.notFound().build());
    }

    //metodos de ayuda
    //convertir el json en modelo
    private Sale toDomain (SaleDto.SaleRequest request){
        Sale sale = new Sale();
        List<DetailSale> details = new ArrayList<>();
        if (request.details() !=null){
            for (SaleDto.DetailRequest itemDto : request.details()){
                DetailSale detail = new DetailSale();
                detail.setQuantity(itemDto.quantity());
                Product product = new Product();
                product.setId(itemDto.productId());
                detail.setProduct(product);
                details.add(detail);
            }
        }
        sale.setDetails(details);
        return sale;
    }
    //convertir el modelo a json
    private SaleDto.SaleResponse toResponse(Sale sale) {
        List<SaleDto.DetailResponse> detailResponses = new ArrayList<>();
        if (sale.getDetails() != null) {
            detailResponses = sale.getDetails().stream()
                    .map(d -> new SaleDto.DetailResponse(
                            d.getProduct().getId(),
                            d.getProduct().getName(),
                            d.getQuantity(),
                            d.getPrice(),
                            d.getSubtotal()
                    )).collect(Collectors.toList());
        }
        return new SaleDto.SaleResponse(
                sale.getId(),
                sale.getDate(),
                sale.getTotal(),
                sale.getUser() != null ? sale.getUser().getId() : null,
                detailResponses
        );
    }

}
