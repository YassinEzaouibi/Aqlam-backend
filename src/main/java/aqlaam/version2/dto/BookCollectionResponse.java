package aqlaam.version2.dto;

import aqlaam.version2.model.enums.BookCollectionType;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link aqlaam.version2.model.BookCollection}
 */
@Value
public class BookCollectionResponse implements Serializable {
    Long id;
    String title;
    String description;
    BookCollectionType type;
    List<BookDto1> books;
    Long userId;
    String userUserName;

    /**
     * DTO for {@link aqlaam.version2.model.Book}
     */
    @Value
    public static class BookDto1 implements Serializable {
        Long id;
        String title;
        String reference;
    }
}