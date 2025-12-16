package pe.edu.upeu.backend_ventas.infrastructure.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.backend_ventas.domain.model.User;
import pe.edu.upeu.backend_ventas.domain.port.in.UserUseCase;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.controller.dto.UserDto;
import pe.edu.upeu.backend_ventas.infrastructure.entity.Rol;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto.UserResponse> registerUser(@RequestBody UserDto.UserRequest userRequest){
        User u = new User();
        u.setFullName(userRequest.fullName());
        u.setUsername(userRequest.username());
        u.setClave(userRequest.clave());
        u.setRol(userRequest.rol()!=null ? userRequest.rol(): Rol.VENDEDOR);
        u.setAddress(userRequest.address());
        u.setPhone(userRequest.phone());
        User registeredUser = userUseCase.registerUser(u);
        UserDto.UserResponse response = new UserDto.UserResponse(
                registeredUser.getId(),
                registeredUser.getFullName(),
                registeredUser.getUsername(),
                registeredUser.getRol(),
                registeredUser.getAddress(),
                registeredUser.getPhone()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto.UserResponse> getUserById(@PathVariable Long id) {
        return userUseCase.getUserById(id)
                .map(user -> ResponseEntity.ok(mapToUserResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    private UserDto.UserResponse mapToUserResponse(User user){
        return new UserDto.UserResponse(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getRol(),
                user.getAddress(),
                user.getPhone()
        );
    }

}
