package pe.edu.upeu.backend_ventas.infrastructure.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.edu.upeu.backend_ventas.domain.model.DetailSale;
import pe.edu.upeu.backend_ventas.domain.model.Sale;
import pe.edu.upeu.backend_ventas.infrastructure.entity.DetailSaleEntity;
import pe.edu.upeu.backend_ventas.infrastructure.entity.SaleEntity;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface SaleMapper {
    Sale toDomain(SaleEntity entity);

    @Mapping(target = "product",source = "product")
    @Mapping(target = "sale", ignore = true)
    DetailSale toDetailDomain(DetailSaleEntity entity);

    List<Sale> toDomainList(List<SaleEntity> entities);

    @Mapping(target = "details", ignore = true)
    SaleEntity toEntity(Sale sale);

    @Mapping(target = "product",source = "product")
    @Mapping(target = "sale", ignore = true)
    DetailSaleEntity toDetailEntity (DetailSale detail);

}
