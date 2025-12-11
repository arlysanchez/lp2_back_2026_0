package pe.edu.upeu.backend_ventas.infrastructure.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.backend_ventas.infrastructure.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {


}
