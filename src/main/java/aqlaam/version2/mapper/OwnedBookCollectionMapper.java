package aqlaam.version2.mapper;

import aqlaam.version2.dto.request.OwnedBookCollectionRequest;
import aqlaam.version2.dto.response.OwnedBookCollectionResponse;
import aqlaam.version2.model.Book;
import aqlaam.version2.model.collections.OwnedBookCollection;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnedBookCollectionMapper {
    @Mapping(source = "userId", target = "user.id")
    OwnedBookCollection toEntity(OwnedBookCollectionRequest ownedBookCollectionRequest);

    @Mapping(target = "bookIds", expression = "java(booksToBookIds(ownedBookCollection.getBooks()))")
    @Mapping(source = "user.id", target = "userId")
    OwnedBookCollectionRequest toDto(OwnedBookCollection ownedBookCollection);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userId", target = "user.id")
    OwnedBookCollection partialUpdate(OwnedBookCollectionRequest ownedBookCollectionRequest, @MappingTarget OwnedBookCollection ownedBookCollection);

    default List<Long> booksToBookIds(List<Book> books) {
        return books.stream().map(Book::getId).toList();
    }

    @Mapping(source = "userId", target = "user.id")
    OwnedBookCollection toEntity(OwnedBookCollectionResponse ownedBookCollectionResponse);

    @Mapping(target = "bookIds", expression = "java(booksToBookIds(ownedBookCollection.getBooks()))")
    @Mapping(source = "user.id", target = "userId")
    OwnedBookCollectionResponse toDto1(OwnedBookCollection ownedBookCollection);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userId", target = "user.id")
    OwnedBookCollection partialUpdate(OwnedBookCollectionResponse ownedBookCollectionResponse, @MappingTarget OwnedBookCollection ownedBookCollection);
}