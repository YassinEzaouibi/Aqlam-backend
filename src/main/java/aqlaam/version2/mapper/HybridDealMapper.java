package aqlaam.version2.mapper;

import aqlaam.version2.dto.request.HybridDealRequest;
import aqlaam.version2.dto.response.HybridDealResponse;
import aqlaam.version2.model.collections.OwnedBookCollection;
import aqlaam.version2.model.deals.HybridDeal;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface HybridDealMapper {
    @Mapping(source = "secondUserIdId", target = "secondUser.id")
    @Mapping(source = "firstUserIdId", target = "firstUser.id")
    HybridDeal toEntity(HybridDealRequest hybridDealRequest);

    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(target = "ownedBookCollections1Ids", expression = "java(ownedBookCollections1ToOwnedBookCollections1Ids(hybridDeal.getOwnedBookCollections1()))")
    @Mapping(target = "ownedBookCollections2Ids", expression = "java(ownedBookCollections2ToOwnedBookCollections2Ids(hybridDeal.getOwnedBookCollections2()))")
    HybridDealRequest toDto(HybridDeal hybridDeal);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    HybridDeal partialUpdate(HybridDealRequest hybridDealRequest, @MappingTarget HybridDeal hybridDeal);

    default List<Long> ownedBookCollections1ToOwnedBookCollections1Ids(List<OwnedBookCollection> ownedBookCollections1) {
        return ownedBookCollections1.stream().map(OwnedBookCollection::getId).toList();
    }

    default List<Long> ownedBookCollections2ToOwnedBookCollections2Ids(List<OwnedBookCollection> ownedBookCollections2) {
        return ownedBookCollections2.stream().map(OwnedBookCollection::getId).toList();
    }

    @Mapping(source = "secondUserIdUserName", target = "secondUser.userName")
    @Mapping(source = "secondUserIdId", target = "secondUser.id")
    @Mapping(source = "firstUserIdUserName", target = "firstUser.userName")
    @Mapping(source = "firstUserIdId", target = "firstUser.id")
    HybridDeal toEntity(HybridDealResponse hybridDealResponse);

    @InheritInverseConfiguration(name = "toEntity")
    HybridDealResponse toDto1(HybridDeal hybridDeal);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    HybridDeal partialUpdate(HybridDealResponse hybridDealResponse, @MappingTarget HybridDeal hybridDeal);
}