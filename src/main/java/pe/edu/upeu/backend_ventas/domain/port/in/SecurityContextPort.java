package pe.edu.upeu.backend_ventas.domain.port.in;

import java.util.Optional;

public interface SecurityContextPort {
    Optional<String> getCurrentUserEmail();
    Optional<Long> getCurrentUserId();
}
