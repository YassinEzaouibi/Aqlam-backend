package aqlaam.version2.mapper;

import aqlaam.version2.dto.UserDto;
import aqlaam.version2.model.actors.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToEntity(UserDto userDto);
    UserDto entityToDto(User user);
}
