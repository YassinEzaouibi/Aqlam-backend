package aqlaam.version2.controller;

import aqlaam.version2.dto.BookCollectionRequest;
import aqlaam.version2.dto.BookCollectionResponse;
import aqlaam.version2.model.enums.BookCollectionType;
import aqlaam.version2.service.implemntation.BookCollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/collection")
@RequiredArgsConstructor
public class BookCollectionController {

    private final BookCollectionService collectionService;
    private static final Logger logger = LoggerFactory.getLogger(BookCollectionController.class);

    @PostMapping("/add")
    ResponseEntity<BookCollectionResponse> createCollection(
            @RequestBody @Valid BookCollectionRequest collectionRequest
    ) {
        logger.info("create a book collection with this data (from the controller) : {} ", collectionRequest);
        BookCollectionResponse returnedCollection = collectionService.add(collectionRequest);
        logger.info("create a book collection with this data (from the controller) : {} ", collectionRequest);
        return new ResponseEntity<>(returnedCollection, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    ResponseEntity<List<BookCollectionResponse>> getAllCollections() {
        logger.info("Fetching all book collections");
        List<BookCollectionResponse> bookCollections = collectionService.getAllCollections();
        return new ResponseEntity<>(bookCollections, HttpStatus.OK);
    }

    @GetMapping("/title")
    ResponseEntity<BookCollectionResponse> getCollectionByTitle(@RequestParam String title) {
        logger.info("Fetching book collection with title: {}", title);
        BookCollectionResponse bookCollection = collectionService.getCollectionByTitle(title);
        return new ResponseEntity<>(bookCollection, HttpStatus.OK);
    }

    @GetMapping("/type")
    ResponseEntity<List<BookCollectionResponse>> getCollectionsByType(@RequestParam BookCollectionType type) {
        logger.info("Fetching book collection with title: {}", type);
        List<BookCollectionResponse> bookCollection = collectionService.getCollectionByType(type);
        return new ResponseEntity<>(bookCollection, HttpStatus.OK);
    }


    @GetMapping("{id}")
    ResponseEntity<BookCollectionResponse> getCollection(@PathVariable Long id) {
        logger.info("Fetching book collection with id: {}", id);
        BookCollectionResponse bookCollectionDto = collectionService.getCollectionById(id);
        return new ResponseEntity<>(bookCollectionDto, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<BookCollectionResponse> updateCollection(
            @PathVariable Long id,
            @RequestBody @Valid BookCollectionRequest collectionRequest
    ) {
        logger.info("Updating book collection with id: {}", id);
        BookCollectionResponse updatedCollection = collectionService.update(id, collectionRequest);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        logger.info("Deleting book collection with id: {}", id);
        collectionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
