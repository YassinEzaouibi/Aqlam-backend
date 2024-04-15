package aqlaam.version2.mapper;

import aqlaam.version2.dto.PersonDto;
import aqlaam.version2.model.actors.Person;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {
    Person toEntity(PersonDto personDto);

    PersonDto toDto(Person person);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Person partialUpdate(PersonDto personDto, @MappingTarget Person person);
}