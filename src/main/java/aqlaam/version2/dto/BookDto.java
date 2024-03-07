package aqlaam.version2.dto;


import aqlaam.version2.model.Book;
import aqlaam.version2.model.enums.BookCover;
import aqlaam.version2.model.enums.Language;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO for {@link Book}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @Past(message = "Publication date should be in the past")
    private Date publicationDate;

    @Min(value = 1, message = "Pages must be greater than 0")
    private int pages;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Book cover is mandatory")
    private BookCover cover;

    @NotNull(message = "Language is mandatory")
    private Language language;

    @NotBlank(message = "Publisher is mandatory")
    private String publisher;

    @NotNull(message = "Category is mandatory")
    private Long categoryId;

    private String bookPicture;

}

