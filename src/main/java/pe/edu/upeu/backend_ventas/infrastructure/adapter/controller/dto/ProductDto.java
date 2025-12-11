package pe.edu.upeu.backend_ventas.infrastructure.adapter.controller.dto;


public record ProductDto() {
    public record ProductRequest(String name, String description,
                                 Double price, Integer stock){}

    public record ProductResponse(Long id, String name,
                                  String description,
                                  Double price, Integer stock, String imageUrl){ }


}
