package aqlaam.version2.dto.response;

import aqlaam.version2.model.deals.BookDeal;
import aqlaam.version2.model.enums.DealStatus;
import aqlaam.version2.model.enums.DealType;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link BookDeal}
 */
@Value
public class BookDealResponse implements Serializable {
    Long id;
    Long firstUserIdId;
    Long secondUserIdId;
    String description;
    DealStatus dealStatus;
    DealType dealType;
    LocalDateTime createdAt;
    Long firstUserCollectionId;
    Long secondUserCollectionId;
    Boolean isDeleted;
}