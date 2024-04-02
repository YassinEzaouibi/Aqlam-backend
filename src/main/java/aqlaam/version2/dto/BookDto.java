package aqlaam.version2.dto;

import aqlaam.version2.model.enums.BookCover;
import aqlaam.version2.model.enums.Category;
import aqlaam.version2.model.enums.Language;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link aqlaam.version2.model.Book}
 */
@Builder
@Value
public class BookDto implements Serializable {

    @NotBlank(message = "Title is mandatory")
    String title;

    @NotBlank(message = "Author is mandatory")
    String author;

    @Past(message = "Publication date should be in the past")
    LocalDate publicationDate;

    @Min(value = 1, message = "Pages must be greater than 0")
    int pages;

    @NotBlank(message = "Description is mandatory")
    String description;

    @NotNull(message = "Book cover is mandatory")
    BookCover cover;

    @NotNull(message = "Language is mandatory")
    Language language;

    @NotBlank(message = "Publisher is mandatory")
    String publisher;

    @NotNull(message = "Category is mandatory")
    Category category;

    @NotNull(message = "Reference is mandatory")
    String reference;

    @Nullable
    String bookPicture;

    @NotNull(message = "User id is mandatory")
    Long userId;
}