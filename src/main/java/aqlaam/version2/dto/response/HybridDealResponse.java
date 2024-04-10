package aqlaam.version2.dto.response;

import aqlaam.version2.model.enums.DealStatus;
import aqlaam.version2.model.enums.DealType;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link aqlaam.version2.model.deals.HybridDeal}
 */
@Value
public class HybridDealResponse implements Serializable {
    Long id;
    Long firstUserIdId;
    String firstUserIdUserName;
    Long secondUserIdId;
    String secondUserIdUserName;
    String description;
    DealStatus dealStatus;
    DealType dealType;
    LocalDateTime createdAt;
    List<OwnedBookCollectionDto1> ownedBookCollections1;
    List<OwnedBookCollectionDto2> ownedBookCollections2;
    double amountGivenByTheFirstUser;
    double amountGivenByTheSecondUser;

    /**
     * DTO for {@link aqlaam.version2.model.collections.OwnedBookCollection}
     */
    @Value
    public static class OwnedBookCollectionDto1 implements Serializable {
        Long id;
        String title;
    }

    /**
     * DTO for {@link aqlaam.version2.model.collections.OwnedBookCollection}
     */
    @Value
    public static class OwnedBookCollectionDto2 implements Serializable {
        Long id;
        String title;
    }
}