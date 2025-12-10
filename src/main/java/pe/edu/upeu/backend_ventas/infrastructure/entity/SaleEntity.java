package pe.edu.upeu.backend_ventas.infrastructure.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ventas")
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

     @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
     private List<DetailSaleEntity> details;

    public SaleEntity() {
    }

    public SaleEntity(Long id, LocalDateTime date, Double total, UserEntity user, List<DetailSaleEntity> details) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.user = user;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<DetailSaleEntity> getDetails() {
        return details;
    }

    public void setDetails(List<DetailSaleEntity> details) {
        this.details = details;
    }
}
