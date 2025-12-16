package pe.edu.upeu.backend_ventas.domain.port.in;

import pe.edu.upeu.backend_ventas.domain.model.User;

import java.util.Optional;

public interface UserUseCase {
    User registerUser(User user);
    Optional<User> getUserById(Long id);
}
