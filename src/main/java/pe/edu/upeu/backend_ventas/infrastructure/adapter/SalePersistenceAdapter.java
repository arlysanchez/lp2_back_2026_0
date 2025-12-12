package pe.edu.upeu.backend_ventas.infrastructure.adapter;

import org.springframework.stereotype.Component;
import pe.edu.upeu.backend_ventas.domain.model.DetailSale;
import pe.edu.upeu.backend_ventas.domain.model.Sale;
import pe.edu.upeu.backend_ventas.domain.port.on.SaleRepositoryPort;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.mapper.SaleMapper;
import pe.edu.upeu.backend_ventas.infrastructure.entity.DetailSaleEntity;
import pe.edu.upeu.backend_ventas.infrastructure.entity.SaleEntity;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SalePersistenceAdapter implements SaleRepositoryPort {
private final SaleRepository saleRepository;
private final SaleMapper saleMapper;

    public SalePersistenceAdapter(SaleRepository saleRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
    }

    @Override
    public Sale save(Sale sale) {
        SaleEntity saleEntity = saleMapper.toEntity(sale);
        List<DetailSaleEntity> detailEntities = new ArrayList<>();
        if (sale.getDetails() !=null) {
            for(DetailSale detailDomain : sale.getDetails()) {
            DetailSaleEntity detailSaleEntity = saleMapper.toDetailEntity(detailDomain);
             detailSaleEntity.setSale(saleEntity);
                detailEntities.add(detailSaleEntity);
            }
        }
        saleEntity.setDetails(detailEntities);
        SaleEntity savedEntity = saleRepository.save(saleEntity);
        return saleMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }

    @Override
    public List<Sale> findAll() {
        return saleMapper.toDomainList(saleRepository.findAll());
    }

    @Override
    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id).map(saleMapper::toDomain);
    }
}
