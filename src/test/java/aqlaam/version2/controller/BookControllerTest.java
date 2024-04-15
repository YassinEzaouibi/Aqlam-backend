package aqlaam.version2.controller;

import aqlaam.version2.controller.BookController;
import aqlaam.version2.dto.BookDto;
import aqlaam.version2.model.enums.Category;
import aqlaam.version2.service.interfaces.IBookService;
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

class BookControllerTest {

    @Mock
    private IBookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateBook() {
        BookDto bookDto = mock(BookDto.class);

        when(bookService.add(any(BookDto.class))).thenReturn(bookDto);

        ResponseEntity<BookDto> responseEntity = bookController.createBook(bookDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(bookDto, responseEntity.getBody());
    }

    @Test
    void testRetrieveAllBooks() {
        List<BookDto> books = new ArrayList<>();
        BookDto book1 = mock(BookDto.class);
        BookDto book2 = mock(BookDto.class);
        books.add(book1);
        books.add(book2);

        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<BookDto>> responseEntity = bookController.retrieveAllBooks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }

    @Test
    void testRetrieveBookByTitle() {
        BookDto bookDto = mock(BookDto.class);
        String title = "Book Title";

        when(bookService.getBookByTitle(title)).thenReturn(bookDto);

        ResponseEntity<BookDto> responseEntity = bookController.retrieveBookByTitle(title);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bookDto, responseEntity.getBody());
    }

    @Test
    void testRetrieveBooksByCategory() {
        List<BookDto> books = new ArrayList<>();
        Category category = Category.FANTASY;
        BookDto book1 = mock(BookDto.class);
        BookDto book2 = mock(BookDto.class);
        books.add(book1);
        books.add(book2);

        when(bookService.getBooksByCategory(category)).thenReturn(books);

        ResponseEntity<List<BookDto>> responseEntity = bookController.retrieveBooksByCategory(category);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }

    @Test
    void testRetrieveBookByAuthor() {
        List<BookDto> books = new ArrayList<>();
        String author = "Author";
        BookDto book1 = mock(BookDto.class);
        BookDto book2 = mock(BookDto.class);
        books.add(book1);
        books.add(book2);

        when(bookService.getBooksByAuthor(author)).thenReturn(books);

        ResponseEntity<List<BookDto>> responseEntity = bookController.retrieveBookByAuthor(author);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }

    @Test
    void testUpdateBook() {
        Long id = 1L;
        BookDto bookDto = mock(BookDto.class);

        when(bookService.update(id, bookDto)).thenReturn(bookDto);

        ResponseEntity<BookDto> responseEntity = bookController.updateBook(id, bookDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bookDto, responseEntity.getBody());
    }

    @Test
    void testDeleteBookByTitle() {
        String title = "Book Title";

        ResponseEntity<Void> responseEntity = bookController.deleteBookByTitle(title);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteBookById() {
        Long id = 1L;

        ResponseEntity<Void> responseEntity = bookController.deleteBookById(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
