package pe.edu.upeu.backend_ventas.infrastructure.adapter.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SaleDto() {

    public record SaleRequest(List<DetailRequest> details){}
    public record DetailRequest(Long productId, Integer quantity){}

    public record SaleResponse(
            Long id,
            LocalDateTime date,
            Double total,
            Long userId,
            List<DetailResponse>details
            ){}

    public record DetailResponse(
            Long productId,
            String productName,
            Integer quantity,
            Double price,
            Double subtotal
    ) {}
}
