package aqlaam.version2.dto.request;

import aqlaam.version2.model.enums.DealType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link aqlaam.version2.model.deals.HybridDeal}
 */
@Value
public class HybridDealRequest implements Serializable {

    @NotNull(message = "First User Id cannot be null")
    Long firstUserIdId;

    @NotNull(message = "Second User Id cannot be null")
    Long secondUserIdId;

    @NotBlank(message = "Description cannot be blank")
    String description;

    @NotNull(message = "Deal Type cannot be null")
    DealType dealType;

    @Size(min = 1, message = "At least one book collection must be provided for the first user")
    List<Long> ownedBookCollections1Ids;

    @Size(min = 1, message = "At least one book collection must be provided for the second user")
    List<Long> ownedBookCollections2Ids;

    @Min(value = 0, message = "Amount given by the first user cannot be negative")
    double amountGivenByTheFirstUser;

    @Min(value = 0, message = "Amount given by the second user cannot be negative")
    double amountGivenByTheSecondUser;
}