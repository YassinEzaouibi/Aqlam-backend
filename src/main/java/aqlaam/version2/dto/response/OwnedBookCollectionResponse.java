package aqlaam.version2.dto.response;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link aqlaam.version2.model.collections.OwnedBookCollection}
 */
@Value
public class OwnedBookCollectionResponse implements Serializable {
    String title;
    String description;
    List<Long> bookIds;
    Long userId;
}