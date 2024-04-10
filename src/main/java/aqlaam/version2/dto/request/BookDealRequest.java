package aqlaam.version2.dto.request;

import aqlaam.version2.model.enums.DealType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link aqlaam.version2.model.deals.BookDeal}
 */
@Value
public class BookDealRequest implements Serializable {

    @NotNull(message = "First User Id cannot be null")
    Long firstUserId;

    @NotNull(message = "Second User Id cannot be null")
    Long secondUserId;

    @Size(min = 10, max = 5000, message = "Description should not be less than 5000 and not be greater than 10 characters")
    String description;

    @NotNull(message = "Deal Type cannot be null")
    DealType dealType;

    @NotNull(message = "Deal Type cannot be null")
    Long firstUserCollectionId;

    @NotNull(message = "Deal Type cannot be null")
    Long secondUserCollectionId;


}