package pe.edu.upeu.backend_ventas.infrastructure.adapter.controller.dto;

public record AuthDto() {
    public record LoginRequest(String email, String password) {}
    public record AuthResponse(String token, UserDto.UserResponse user) {}
}
