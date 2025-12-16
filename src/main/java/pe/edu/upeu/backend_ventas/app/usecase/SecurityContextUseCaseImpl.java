package pe.edu.upeu.backend_ventas.app.usecase;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pe.edu.upeu.backend_ventas.domain.model.User;
import pe.edu.upeu.backend_ventas.domain.port.in.SecurityContextPort;
import pe.edu.upeu.backend_ventas.domain.port.on.UserRepositoryPort;

import java.util.Optional;

@Service
public class SecurityContextUseCaseImpl implements SecurityContextPort {
    private final UserRepositoryPort userRepositoryPort;

    public SecurityContextUseCaseImpl(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public Optional<String> getCurrentUserEmail() {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (principal instanceof UserDetails){
          return Optional.of(((UserDetails) principal).getUsername());
      }else if(principal instanceof  String){
          return Optional.of((String) principal);
      }
      return Optional.empty();

    }

    @Override
    public Optional<Long> getCurrentUserId() {
        return getCurrentUserEmail()
                .flatMap(userRepositoryPort::findByEmail)
                .map(User::getId);
    }
}
