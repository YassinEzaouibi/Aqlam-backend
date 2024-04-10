package aqlaam.version2.mapper;

import aqlaam.version2.dto.request.BookDealRequest;
import aqlaam.version2.model.deals.BookDeal;
import aqlaam.version2.dto.response.BookDealResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookDealMapper {
    @Mapping(source = "firstUserCollectionId", target = "firstUserCollection.id")
    @Mapping(source = "secondUserCollectionId", target = "secondUserCollection.id")
    @Mapping(source = "firstUserId", target = "firstUser.id")
    @Mapping(source = "secondUserId", target = "secondUser.id")
    BookDeal toEntity(BookDealRequest bookDealRequest);

    @InheritInverseConfiguration(name = "toEntity")
    BookDealRequest toDto(BookDeal bookDeal);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BookDeal partialUpdate(BookDealRequest bookDealRequest, @MappingTarget BookDeal bookDeal);

    @Mapping(source = "secondUserCollectionId", target = "secondUserCollection.id")
    @Mapping(source = "firstUserCollectionId", target = "firstUserCollection.id")
    @Mapping(source = "secondUserIdId", target = "secondUser.id")
    @Mapping(source = "firstUserIdId", target = "firstUser.id")
    BookDeal toEntity(BookDealResponse bookDealResponse);

    @InheritInverseConfiguration(name = "toEntity")
    BookDealResponse toDto1(BookDeal bookDeal);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BookDeal partialUpdate(BookDealResponse bookDealResponse, @MappingTarget BookDeal bookDeal);
}