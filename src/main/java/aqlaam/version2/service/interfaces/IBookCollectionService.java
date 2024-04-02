package aqlaam.version2.service.interfaces;


import aqlaam.version2.dto.BookCollectionRequest;
import aqlaam.version2.dto.BookCollectionResponse;

import java.util.List;

public interface IBookCollectionService {

    List<BookCollectionResponse> getAllCollections();
    BookCollectionResponse add(BookCollectionRequest collectionRequest);
    BookCollectionResponse update(Long id, BookCollectionRequest collectionRequest);
    void deleteById(Long id);
    BookCollectionResponse getCollectionById(Long id);

}
