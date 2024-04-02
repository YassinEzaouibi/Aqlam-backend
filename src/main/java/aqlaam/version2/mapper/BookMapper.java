package aqlaam.version2.mapper;

import aqlaam.version2.dto.BookDto;
import aqlaam.version2.model.Book;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {

    @Mapping(source = "userId", target = "user.id")
    Book toEntity(BookDto bookDto);

    @InheritInverseConfiguration(name = "toEntity")
    BookDto toDto(Book book);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Book partialUpdate(BookDto bookDto, @MappingTarget Book book);

}