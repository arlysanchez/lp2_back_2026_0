package pe.edu.upeu.backend_ventas.app.usecase;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.edu.upeu.backend_ventas.domain.model.DetailSale;
import pe.edu.upeu.backend_ventas.domain.model.Product;
import pe.edu.upeu.backend_ventas.domain.model.Sale;
import pe.edu.upeu.backend_ventas.domain.model.User;
import pe.edu.upeu.backend_ventas.domain.port.in.SaleUseCase;
import pe.edu.upeu.backend_ventas.domain.port.on.ProductRepositoryPort;
import pe.edu.upeu.backend_ventas.domain.port.on.SaleRepositoryPort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaleUseCaseImpl implements SaleUseCase {

    private final SaleRepositoryPort saleRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;

    public SaleUseCaseImpl(SaleRepositoryPort saleRepositoryPort, ProductRepositoryPort productRepositoryPort) {
        this.saleRepositoryPort = saleRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public Sale createSale(Sale sale) {
        sale.setDate(LocalDateTime.now());
        User user = new User();
        user.setId(1L);
        sale.setUser(user);
        calculateSaleDetails(sale,sale.getDetails());
        return saleRepositoryPort.save(sale);

    }

    @Override
    @Transactional
    public Sale updateSale(Long saleId, Sale sale) {
      Sale existingSale = saleRepositoryPort.findById(saleId)
              .orElseThrow(()-> new RuntimeException("venta no encontrada con el id"+saleId));

        if (sale.getUser() !=null){
            existingSale.setUser(sale.getUser());
        }
        calculateSaleDetails(existingSale,sale.getDetails());
        return saleRepositoryPort.save(existingSale);

    }

    @Override
    @Transactional
    public boolean deleteSale(Long id) {
        if (saleRepositoryPort.findById(id).isPresent()){
            saleRepositoryPort.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public List<Sale> getAllSales() {
        return saleRepositoryPort.findAll();
    }
    @Override
    public Optional<Sale> getAllSaleById(Long id) {
        return saleRepositoryPort.findById(id);
    }


    private void calculateSaleDetails(Sale saleToUpdate, List<DetailSale> inputsDetails){

        List<DetailSale> processedDetails = new ArrayList<>();
        double totalSale = 0.0;

        if (inputsDetails !=null){
            for (DetailSale item : inputsDetails) {

                Long productId = item.getProduct().getId();
                //buscar el precio real del producto en la bd

                Product productDB = productRepositoryPort.fillById(productId)
                        .orElseThrow(() -> new RuntimeException("producto no encontrado id:" + productId));
                //validar Stock

                if (productDB.getStock() < item.getQuantity()) {
                    throw new RuntimeException("Stock insuficiente para el producto" + productDB.getName());
                }
                //calculamos el subtotal con el precio real de BD
                double subtotal = productDB.getPrice() * item.getQuantity();
                totalSale += subtotal;

                //crear el detalle
                DetailSale detail = new DetailSale();
                detail.setQuantity(item.getQuantity());
                detail.setPrice(productDB.getPrice());
                detail.setSubtotal(subtotal);
                detail.setProduct(productDB);

                processedDetails.add(detail);
            }
        }
        saleToUpdate.setTotal(totalSale);
        saleToUpdate.setDetails(processedDetails);

    }
}
