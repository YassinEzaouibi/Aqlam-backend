package aqlaam.version2.controller;

import aqlaam.version2.controller.BookCollectionController;
import aqlaam.version2.dto.request.OwnedBookCollectionRequest;
import aqlaam.version2.dto.response.OwnedBookCollectionResponse;
import aqlaam.version2.service.interfaces.IBookCollectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookCollectionControllerTest {

    @Mock
    private IBookCollectionService collectionService;

    @InjectMocks
    private BookCollectionController collectionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateCollection() {
        OwnedBookCollectionRequest collectionRequest = mock(OwnedBookCollectionRequest.class);
        OwnedBookCollectionResponse collectionResponse = mock(OwnedBookCollectionResponse.class);

        when(collectionService.addOwnedCollection(any(OwnedBookCollectionRequest.class))).thenReturn(collectionResponse);

        ResponseEntity<OwnedBookCollectionResponse> responseEntity = collectionController.createCollection(collectionRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(collectionResponse, responseEntity.getBody());
    }

    @Test
    void testGetAllCollections() {
        List<OwnedBookCollectionResponse> collections = new ArrayList<>();
        OwnedBookCollectionResponse collection1 = mock(OwnedBookCollectionResponse.class);
        OwnedBookCollectionResponse collection2 = mock(OwnedBookCollectionResponse.class);
        collections.add(collection1);
        collections.add(collection2);

        when(collectionService.getAllCollections()).thenReturn(collections);

        ResponseEntity<List<OwnedBookCollectionResponse>> responseEntity = collectionController.getAllCollections();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(collections, responseEntity.getBody());
    }

    @Test
    void testGetCollectionByTitle() {
        String title = "Book Collection Title";
        OwnedBookCollectionResponse collectionResponse = mock(OwnedBookCollectionResponse.class);

        when(collectionService.getCollectionByTitle(title)).thenReturn(collectionResponse);

        ResponseEntity<OwnedBookCollectionResponse> responseEntity = collectionController.getCollectionByTitle(title);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(collectionResponse, responseEntity.getBody());
    }

    @Test
    void testGetCollection() {
        Long id = 1L;
        OwnedBookCollectionResponse collectionResponse = mock(OwnedBookCollectionResponse.class);

        when(collectionService.getCollectionById(id)).thenReturn(collectionResponse);

        ResponseEntity<OwnedBookCollectionResponse> responseEntity = collectionController.getCollection(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(collectionResponse, responseEntity.getBody());
    }

    @Test
    void testUpdateCollection() {
        Long id = 1L;
        OwnedBookCollectionRequest collectionRequest = mock(OwnedBookCollectionRequest.class);
        OwnedBookCollectionResponse updatedCollection = mock(OwnedBookCollectionResponse.class);

        when(collectionService.update(id, collectionRequest)).thenReturn(updatedCollection);

        ResponseEntity<OwnedBookCollectionResponse> responseEntity = collectionController.updateCollection(id, collectionRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedCollection, responseEntity.getBody());
    }

    @Test
    void testDeleteCollection() {
        Long id = 1L;

        ResponseEntity<Void> responseEntity = collectionController.deleteCollection(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
