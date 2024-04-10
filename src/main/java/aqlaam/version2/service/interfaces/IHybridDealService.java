package aqlaam.version2.service.interfaces;

import aqlaam.version2.dto.request.HybridDealRequest;
import aqlaam.version2.dto.response.HybridDealResponse;

import java.util.List;

public interface IHybridDealService {

    HybridDealResponse add(HybridDealRequest hybridDealRequest);
    List<HybridDealResponse> getAllHybridDeals();
    HybridDealResponse getHybridDealById(Long id);
    void deleteDealById(Long id);
    HybridDealResponse acceptDeal(Long id);
    HybridDealResponse rejectDeal(Long id);
    List<HybridDealResponse> getAllAcceptedDeals(Long userId);
    List<HybridDealResponse> getAllRejectedDeals(Long userId);
    List<HybridDealResponse> getAllPendedDeals(Long userId);

}
