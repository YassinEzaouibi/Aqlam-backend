package aqlaam.version2.dto.response;

import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.enums.Sex;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link User}
 */
@Value
public class UserResponse implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String email;
    String password;
    Date dateOfBirth;
    Sex sex;
    String userName;
    String profilePicture;
    List<OwnedBookCollectionDto> booksCollections;
    List<Long> favoriteBookCollectionIds;
    List<BookDto> books;

    /**
     * DTO for {@link aqlaam.version2.model.collections.OwnedBookCollection}
     */
    @Value
    public static class OwnedBookCollectionDto implements Serializable {
        Long id;
        String title;
    }

    /**
     * DTO for {@link aqlaam.version2.model.Book}
     */
    @Value
    public static class BookDto implements Serializable {
        Long id;
        String title;
        String reference;
    }
}