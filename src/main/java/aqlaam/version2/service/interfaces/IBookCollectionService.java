package aqlaam.version2.service.interfaces;


import aqlaam.version2.dto.request.OwnedBookCollectionRequest;
import aqlaam.version2.dto.response.OwnedBookCollectionResponse;

import java.util.List;

public interface IBookCollectionService {

    List<OwnedBookCollectionResponse> getAllCollections();
    OwnedBookCollectionResponse addOwnedCollection(OwnedBookCollectionRequest collectionRequest);
    OwnedBookCollectionResponse update(Long id, OwnedBookCollectionRequest collectionRequest);
    void deleteById(Long id);
    OwnedBookCollectionResponse getCollectionById(Long id);
    OwnedBookCollectionResponse getCollectionByTitle(String title);


    }
