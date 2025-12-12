package pe.edu.upeu.backend_ventas.domain.port.on;

import pe.edu.upeu.backend_ventas.domain.model.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleRepositoryPort {
    Sale save(Sale sale);
    void deleteById(Long id);
    List<Sale> findAll();
    Optional<Sale> findById(Long id);
}
