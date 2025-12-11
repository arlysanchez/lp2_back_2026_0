package pe.edu.upeu.backend_ventas.infrastructure.adapter.mapper;

import org.mapstruct.Mapper;
import pe.edu.upeu.backend_ventas.domain.model.Product;
import pe.edu.upeu.backend_ventas.infrastructure.entity.ProductEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toDomainModel(ProductEntity entity);

    ProductEntity toEntity(Product product);

    List<Product> toDomainModelList(List<ProductEntity> entities);

}
