package aqlaam.version2.mapper;

import aqlaam.version2.dto.AdminDto;
import aqlaam.version2.model.actors.Admin;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin dtoToEntity(AdminDto adminDto);

    AdminDto entityToDto(Admin admin);

}