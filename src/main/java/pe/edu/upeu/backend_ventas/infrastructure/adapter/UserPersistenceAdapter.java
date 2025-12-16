package pe.edu.upeu.backend_ventas.infrastructure.adapter;

import org.springframework.stereotype.Component;
import pe.edu.upeu.backend_ventas.domain.model.User;
import pe.edu.upeu.backend_ventas.domain.port.on.UserRepositoryPort;
import pe.edu.upeu.backend_ventas.infrastructure.adapter.mapper.UserMapper;
import pe.edu.upeu.backend_ventas.infrastructure.entity.UserEntity;

import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserRepositoryPort{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserPersistenceAdapter(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(userMapper::toDomainModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByUsername(email).map(userMapper::toDomainModel);
    }
}
