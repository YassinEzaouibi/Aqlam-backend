package aqlaam.version2.mapper;

import aqlaam.version2.dto.BookCollectionRequest;
import aqlaam.version2.dto.BookCollectionResponse;
import aqlaam.version2.model.Book;
import aqlaam.version2.model.BookCollection;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookCollectionMapper {
    @Mapping(source = "userId", target = "user.id")
    BookCollection requestToEntity(BookCollectionRequest bookCollectionRequest);

    @Mapping(target = "bookIds", expression = "java(booksToBookIds(bookCollection.getBooks()))")
    @Mapping(source = "user.id", target = "userId")
    BookCollectionRequest entityToRequest(BookCollection bookCollection);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userId", target = "user.id")
    BookCollection partialUpdate(BookCollectionRequest bookCollectionRequest, @MappingTarget BookCollection bookCollection);

    default List<Long> booksToBookIds(List<Book> books) {
        return books.stream().map(Book::getId).collect(Collectors.toList());
    }

    @Mapping(source = "userUserName", target = "user.userName")
    @Mapping(source = "userId", target = "user.id")
    BookCollection responseToEntity(BookCollectionResponse bookCollectionResponse);

    @InheritInverseConfiguration(name = "responseToEntity")
    BookCollectionResponse entityToResponse(BookCollection bookCollection);

    @InheritConfiguration(name = "responseToEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BookCollection partialUpdate(BookCollectionResponse bookCollectionResponse, @MappingTarget BookCollection bookCollection);
}