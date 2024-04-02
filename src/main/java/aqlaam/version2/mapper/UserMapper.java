package aqlaam.version2.mapper;

import aqlaam.version2.dto.UserResponce;
import aqlaam.version2.dto.UserRequest;
import aqlaam.version2.model.actors.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User requestToEntity(UserRequest userRequest);

    UserRequest entityToRequest(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserRequest userRequest, @MappingTarget User user);

    User responceToEntity(UserResponce userResponce);

    @AfterMapping
    default void linkBooksCollections(@MappingTarget User user) {
        user.getBooksCollections().forEach(booksCollection -> booksCollection.setUser(user));
    }

    @AfterMapping
    default void linkBooks(@MappingTarget User user) {
        user.getBooks().forEach(book -> book.setUser(user));
    }

    UserResponce entityToResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserResponce userResponce, @MappingTarget User user);
}