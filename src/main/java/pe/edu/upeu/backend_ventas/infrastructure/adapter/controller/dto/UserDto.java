package pe.edu.upeu.backend_ventas.infrastructure.adapter.controller.dto;

import pe.edu.upeu.backend_ventas.infrastructure.entity.Rol;

public record UserDto() {
    public record UserRequest(String fullName,
                              String username,
                              String clave,
                              Rol rol,
                              String address,String phone){}

    public record UserResponse(Long id,String fullName,
                               String username,
                               Rol rol,
                               String address,String phone ){}

}
