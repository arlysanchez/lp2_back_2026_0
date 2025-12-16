package pe.edu.upeu.backend_ventas.infrastructure.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.backend_ventas.domain.model.User;
import pe.edu.upeu.backend_ventas.domain.port.on.UserRepositoryPort;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.controller.dto.AuthDto;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.controller.dto.UserDto;
import pe.edu.upeu.backend_ventas.infrastructure.security.JwtService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepositoryPort userRepositoryPort;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    public AuthController(AuthenticationManager authenticationManager, UserRepositoryPort userRepositoryPort, JwtService jwtService, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepositoryPort = userRepositoryPort;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }
    @PostMapping("/login")
    public ResponseEntity<AuthDto.AuthResponse> login(@RequestBody AuthDto.LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User userModel = userRepositoryPort.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado después de la autenticación."));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        String token = jwtService.generateToken(userDetails);
        UserDto.UserResponse userResponse = new UserDto.UserResponse(
                userModel.getId(),
                userModel.getFullName(),
                userModel.getUsername(),
                userModel.getRol(),
                userModel.getAddress(),
                userModel.getPhone()
        );
        AuthDto.AuthResponse authResponse = new AuthDto.AuthResponse(token, userResponse);
        return ResponseEntity.ok(authResponse);
    }
    @PostMapping("/validate-token")
    public ResponseEntity<AuthDto.AuthResponse> validateAndRefreshToken(@RequestHeader("Authorization") String authHeader) {
        // 1. Validar que el header de autorización sea correcto
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final String jwt = authHeader.substring(7);

        try {
            // 2. Extraer el email (username) del token
            final String userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null) {
                // 3. Cargar los UserDetails para la validación
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                // 4. Validar que el token sea correcto y no haya expirado
                if (jwtService.isTokenValid(jwt, userDetails)) {

                    // --- Lógica añadida para coincidir con la respuesta del login ---

                    // a. Obtener el modelo de dominio completo del usuario desde la base de datos.
                    User userModel = userRepositoryPort.findByEmail(userEmail)
                            .orElseThrow(() -> new RuntimeException("Usuario del token no encontrado en la base de datos."));

                    // b. Generar un nuevo token para refrescar la sesión del usuario.
                    String newToken = jwtService.generateToken(userDetails);

                    // c. Crear el DTO de respuesta del usuario (sin datos sensibles).
                    UserDto.UserResponse userResponse = new UserDto.UserResponse(
                            userModel.getId(),
                            userModel.getFullName(),
                            userModel.getUsername(),
                            userModel.getRol(),
                            userModel.getAddress(),
                            userModel.getPhone()
                    );

                    // d. Crear la respuesta final con el nuevo token y los datos del usuario.
                    AuthDto.AuthResponse authResponse = new AuthDto.AuthResponse(newToken, userResponse);

                    return ResponseEntity.ok(authResponse);
                }
            }
        } catch (Exception e) {
            // Captura cualquier excepción de JWT (expirado, malformado, etc.)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Si algo falla en el proceso, se devuelve no autorizado
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
