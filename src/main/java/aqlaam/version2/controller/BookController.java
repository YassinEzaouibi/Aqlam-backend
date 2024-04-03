package aqlaam.version2.controller;

import aqlaam.version2.dto.BookDto;
import aqlaam.version2.model.enums.Category;
import aqlaam.version2.service.interfaces.IBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final IBookService bookService;

    @PostMapping("/add")
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto book) {
        BookDto bookDto = bookService.add(book);
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> retrieveAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/title")
    ResponseEntity<BookDto> retrieveBookByTitle(@RequestParam String title) {
        BookDto book = bookService.getBookByTitle(title);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/category")
    ResponseEntity<List<BookDto>> retrieveBooksByCategory(@RequestParam Category category) {
        List<BookDto> books = bookService.getBooksByCategory(category);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/author")
    ResponseEntity<List<BookDto>> retrieveBookByAuthor(@RequestParam String author) {
        List<BookDto> book = bookService.getBooksByAuthor(author);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody @Valid BookDto book) {
        BookDto updatedBook = bookService.update(id, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    ResponseEntity<Void> deleteBookByTitle(@RequestParam String title) {
        bookService.deleteByTitle(title);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
