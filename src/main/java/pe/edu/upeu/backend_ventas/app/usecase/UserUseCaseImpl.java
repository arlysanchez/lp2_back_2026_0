package pe.edu.upeu.backend_ventas.app.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upeu.backend_ventas.domain.exception.UserAlreadyExistsException;
import pe.edu.upeu.backend_ventas.domain.model.User;
import pe.edu.upeu.backend_ventas.domain.port.in.UserUseCase;
import pe.edu.upeu.backend_ventas.domain.port.on.UserRepositoryPort;

import java.util.Optional;

@Service
public class UserUseCaseImpl implements UserUseCase {
    private final UserRepositoryPort userRepositoryPort;

    private final PasswordEncoder passwordEncoder;

    public UserUseCaseImpl(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User registerUser(User user) {
       if (userRepositoryPort.findByEmail(user.getUsername()).isPresent()){
           throw new UserAlreadyExistsException("ya existe un usuario con ese email"+user.getUsername());
       }
       String encryptedPassword= passwordEncoder.encode(user.getClave());
       user.setClave(encryptedPassword);
       return userRepositoryPort.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepositoryPort.findById(id);
    }
}
