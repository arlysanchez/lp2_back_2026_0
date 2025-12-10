package pe.edu.upeu.backend_ventas.domain.model;


import jakarta.persistence.*;
import pe.edu.upeu.backend_ventas.infrastructure.entity.Rol;

public class User {
    private Long id;
    private String username;
    private String clave;
    private String fullName;
    private String address;
    private String phone;
    private Rol rol;

    public User() {
    }

    public User(Long id, String username, String clave, String fullName, String address, String phone, Rol rol) {
        this.id = id;
        this.username = username;
        this.clave = clave;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
