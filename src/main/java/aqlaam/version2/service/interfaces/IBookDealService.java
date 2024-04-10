package aqlaam.version2.service.interfaces;

import aqlaam.version2.dto.request.BookDealRequest;
import aqlaam.version2.dto.response.BookDealResponse;

import java.util.List;

public interface IBookDealService {

    BookDealResponse add(BookDealRequest bookDealRequest);
    List<BookDealResponse> getAllBookDeals();
    BookDealResponse getBookDealById(Long id);
    void deleteDealById(Long id);
    BookDealResponse acceptDeal(Long id);
    BookDealResponse rejectDeal(Long id);
    List<BookDealResponse> getAllAcceptedDeals(Long userId);
    List<BookDealResponse> getAllRejectedDeals(Long userId);
    List<BookDealResponse> getAllPendedDeals(Long userId);
}
