package aqlaam.version2.dto;/*
package com.aqlaam.dto;

import com.aqlaam.model.BookDeal;
import com.aqlaam.model.enums.DealStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

*/
/**
 * DTO for {@link BookDeal}
 *//*



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDealDto {

    private Long id;

    @NotNull(message = "First user is mandatory")
    private Long firstUserId;

    @NotNull(message = "Second user is mandatory")
    private Long secondUserId;

    @NotNull(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Status is mandatory")
    private DealStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime finishedAt;

    @NotNull(message = "First user's book collection is mandatory")
    private Long firstUserCollectionId;

    @NotNull(message = "Second user's book collection is mandatory")
    private Long secondUserCollectionId;
}
*/
