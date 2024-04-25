package aqlaam.version2.mapper;

import aqlaam.version2.dto.AdminDto;
import aqlaam.version2.model.actors.Admin;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminMapper {

    @Mapping(source = "id", target = "id")
    Admin toEntity(AdminDto adminDto);

    @Mapping(source = "id", target = "id")
    AdminDto toDto(Admin admin);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Admin partialUpdate(AdminDto adminDto, @MappingTarget Admin admin);

}