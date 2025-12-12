package pe.edu.upeu.backend_ventas.domain.port.in;

import pe.edu.upeu.backend_ventas.domain.model.Product;
import pe.edu.upeu.backend_ventas.domain.model.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleUseCase {

    Sale createSale(Sale sale);
    Sale updateSale(Long saleId, Sale sale);
    boolean deleteSale(Long id);
    List<Sale> getAllSales();
    Optional<Sale> getAllSaleById(Long id);
}
