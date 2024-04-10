package aqlaam.version2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link aqlaam.version2.model.collections.OwnedBookCollection}
 */
@Value
public class OwnedBookCollectionRequest implements Serializable {

    @NotBlank(message = "Title cannot be blank")
    String title;

    @NotBlank(message = "Description cannot be blank")
    String description;

    @Size(min = 1, message = "At least one book id must be provided")
    List<Long> bookIds;

    @NotNull(message = "User id cannot be null")
    Long userId;
}