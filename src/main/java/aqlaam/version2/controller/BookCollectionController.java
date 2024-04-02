package aqlaam.version2.controller;

import aqlaam.version2.dto.BookCollectionRequest;
import aqlaam.version2.dto.BookCollectionResponse;
import aqlaam.version2.service.implemntation.BCService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class BookCollectionController {

    private final BCService collectionService;
    private static final Logger logger = LoggerFactory.getLogger(BookCollectionController.class);

    /*@GetMapping("{id}")
    ResponseEntity<BCResponse> getCollection(@PathVariable Long id) {
        return;
    }*/

    @PostMapping
    ResponseEntity<BookCollectionResponse> createCollection(
            @RequestBody @Valid BookCollectionRequest collectionRequest
    ) {
        logger.info("create a book collection with this data (from the controller) : {} ", collectionRequest);
        BookCollectionResponse returnedCollection = collectionService.add(collectionRequest);
        logger.info("create a book collection with this data (from the controller) : {} ", collectionRequest);
        return new ResponseEntity<>(returnedCollection, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    ResponseEntity<BookCollectionResponse> getCollection(@PathVariable Long id) {
        logger.info("Fetching book collection with id: {}", id);
        BookCollectionResponse bookCollectionDto = collectionService.getCollectionById(id);
        return new ResponseEntity<>(bookCollectionDto, HttpStatus.OK);
    }

}
