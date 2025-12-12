package pe.edu.upeu.backend_ventas.infrastructure.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.backend_ventas.infrastructure.entity.SaleEntity;

public interface SaleRepository extends JpaRepository<SaleEntity, Long> {

}
