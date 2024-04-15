package aqlaam.version2.controller;

import aqlaam.version2.dto.request.OwnedBookCollectionRequest;
import aqlaam.version2.dto.response.OwnedBookCollectionResponse;
import aqlaam.version2.service.interfaces.IBookCollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/collection")
@RequiredArgsConstructor
public class BookCollectionController {

    private final IBookCollectionService collectionService;
    private static final Logger logger = LoggerFactory.getLogger(BookCollectionController.class);

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<OwnedBookCollectionResponse> createCollection(
            @RequestBody @Valid OwnedBookCollectionRequest collectionRequest
    ) {
        logger.info("create a book collection with this data (from the controller) : {} ", collectionRequest);
        OwnedBookCollectionResponse returnedCollection = collectionService.addOwnedCollection(collectionRequest);
        logger.info("create a book collection with this data (from the controller) : {} ", collectionRequest);
        return new ResponseEntity<>(returnedCollection, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<OwnedBookCollectionResponse>> getAllCollections() {
        logger.info("Fetching all book collections");
        List<OwnedBookCollectionResponse> bookCollections = collectionService.getAllCollections();
        return new ResponseEntity<>(bookCollections, HttpStatus.OK);
    }

    @GetMapping("/title")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<OwnedBookCollectionResponse> getCollectionByTitle(@RequestParam String title) {
        logger.info("Fetching book collection with title: {}", title);
        OwnedBookCollectionResponse bookCollection = collectionService.getCollectionByTitle(title);
        return new ResponseEntity<>(bookCollection, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<OwnedBookCollectionResponse> getCollection(@PathVariable Long id) {
        logger.info("Fetching book collection with id: {}", id);
        OwnedBookCollectionResponse bookCollectionDto = collectionService.getCollectionById(id);
        return new ResponseEntity<>(bookCollectionDto, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<OwnedBookCollectionResponse> updateCollection(
            @PathVariable Long id,
            @RequestBody @Valid OwnedBookCollectionRequest collectionRequest
    ) {
        logger.info("Updating book collection with id: {}", id);
        OwnedBookCollectionResponse updatedCollection = collectionService.update(id, collectionRequest);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        logger.info("Deleting book collection with id: {}", id);
        collectionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
