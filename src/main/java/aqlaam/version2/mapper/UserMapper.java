package aqlaam.version2.mapper;

import aqlaam.version2.model.actors.User;
import aqlaam.version2.dto.request.UserRequest;
import aqlaam.version2.dto.response.UserResponse;
import aqlaam.version2.model.collections.FavoriteBookCollection;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserRequest userRequest);

    UserRequest toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserRequest userRequest, @MappingTarget User user);

    User toEntity(UserResponse userResponse);

    @AfterMapping
    default void linkBooksCollections(@MappingTarget User user) {
        user.getBooksCollections().forEach(booksCollection -> booksCollection.setUser(user));
    }

    @AfterMapping
    default void linkBooks(@MappingTarget User user) {
        user.getBooks().forEach(book -> book.setUser(user));
    }

    @Mapping(target = "favoriteBookCollectionIds", expression = "java(favoriteBookCollectionsToFavoriteBookCollectionIds(user.getFavoriteBookCollections()))")
    UserResponse toDto1(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserResponse userResponse, @MappingTarget User user);

    default List<Long> favoriteBookCollectionsToFavoriteBookCollectionIds(List<FavoriteBookCollection> favoriteBookCollections) {
        return favoriteBookCollections.stream().map(FavoriteBookCollection::getId).collect(Collectors.toList());
    }
}