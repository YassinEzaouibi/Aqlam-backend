package aqlaam.version2.dto;

import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.model.enums.Sex;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link aqlaam.version2.model.actors.User}
 */
@Value
public class UserResponce implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String email;
    String password;
    Date dateOfBirth;
    Sex sex;
    String userName;
    String profilePicture;
    List<BookCollectionDto> booksCollections;
    List<BookDto> books;
    AccountType accountType;

    /**
     * DTO for {@link aqlaam.version2.model.BookCollection}
     */
    @Value
    public static class BookCollectionDto implements Serializable {
        Long id;
        String title;
    }

    /**
     * DTO for {@link aqlaam.version2.model.Book}
     */
    @Value
    public static class BookDto implements Serializable {
        Long id;
        String reference;
    }
}