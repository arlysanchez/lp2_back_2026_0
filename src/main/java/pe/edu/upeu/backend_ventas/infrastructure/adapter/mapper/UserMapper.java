package pe.edu.upeu.backend_ventas.infrastructure.adapter.mapper;

import org.mapstruct.Mapper;
import pe.edu.upeu.backend_ventas.domain.model.User;
import pe.edu.upeu.backend_ventas.infrastructure.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomainModel(UserEntity entity);
    UserEntity toEntity(User user);
}
