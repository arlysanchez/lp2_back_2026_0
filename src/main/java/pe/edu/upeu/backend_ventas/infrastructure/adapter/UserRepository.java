package pe.edu.upeu.backend_ventas.infrastructure.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.backend_ventas.infrastructure.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);


}
