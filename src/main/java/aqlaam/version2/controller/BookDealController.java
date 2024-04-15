package aqlaam.version2.controller;

import aqlaam.version2.dto.request.BookDealRequest;
import aqlaam.version2.dto.response.BookDealResponse;
import aqlaam.version2.service.interfaces.IBookDealService;
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
@RequestMapping("/api/v1/book-deals")
@RequiredArgsConstructor
public class BookDealController {

    private final IBookDealService bookDealService;
    private final Logger logger = LoggerFactory.getLogger(BookDealController.class);


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BookDealResponse> addBookDeal(@RequestBody @Valid BookDealRequest bookDealRequest) {
        logger.info("Creating a new book deal this data (from addBookDeal methode) : {}", bookDealRequest);
        BookDealResponse bookDeal = bookDealService.add(bookDealRequest);
        return new ResponseEntity<>(bookDeal, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<BookDealResponse>> retrieveAllBookDeals() {
        List<BookDealResponse> books = bookDealService.getAllBookDeals();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<BookDealResponse> retrieveBookDeal(@PathVariable Long id) {
        BookDealResponse books = bookDealService.getBookDealById(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping("/{id}/accept")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BookDealResponse> acceptDeal(@PathVariable Long id) {
        BookDealResponse bookDeal = bookDealService.acceptDeal(id);
        return new ResponseEntity<>(bookDeal, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> deleteBookDeal(@PathVariable Long id) {
        bookDealService.deleteDealById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BookDealResponse> rejectDeal(@PathVariable Long id) {
        BookDealResponse bookDealResponse = bookDealService.rejectDeal(id);
        return new ResponseEntity<>(bookDealResponse, HttpStatus.OK);
    }

    @GetMapping("/accepted/{userId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<BookDealResponse>> getAllAcceptedDeals(@PathVariable Long userId) {
        List<BookDealResponse> books = bookDealService.getAllAcceptedDeals(userId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/rejected/{userId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<BookDealResponse>> getAllRejectedDeals(@PathVariable Long userId) {
        List<BookDealResponse> books = bookDealService.getAllRejectedDeals(userId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/pending/{userId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<BookDealResponse>> getAllPendingDeals(@PathVariable Long userId) {
        List<BookDealResponse> books = bookDealService.getAllPendedDeals(userId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

}
