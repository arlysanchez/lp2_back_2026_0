package pe.edu.upeu.backend_ventas.domain.model;


import java.time.LocalDateTime;
import java.util.List;


public class Sale {

    private Long id;
    private LocalDateTime date;
    private Double total;
    private User user;
     private List<DetailSale> details;

    public Sale() {
    }

    public Sale(Long id, LocalDateTime date, Double total, User user, List<DetailSale> details) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DetailSale> getDetails() {
        return details;
    }

    public void setDetails(List<DetailSale> details) {
        this.details = details;
    }
}
