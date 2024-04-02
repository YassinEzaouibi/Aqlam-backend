package aqlaam.version2.dto;

import aqlaam.version2.model.enums.BookCollectionType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link aqlaam.version2.model.BookCollection}
 */
@Value
public class BookCollectionRequest implements Serializable {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    String title;

    @NotBlank(message = "description is required")
    @Size(min = 3, max = 50, message = "Description must be between 5 and 1000 characters")
    String description;

    @NotNull(message = "Type is required")
    BookCollectionType type;

    @NotNull(message = "Book ids are required")
    @Size(min = 2, message = "At least 2 books ids is required")
    List<Long> bookIds;

    @NotNull(message = "User id is required")
    Long userId;
}