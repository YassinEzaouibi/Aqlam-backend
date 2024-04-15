package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.BookDto;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.BookMapper;
import aqlaam.version2.model.Book;
import aqlaam.version2.model.enums.Category;
import aqlaam.version2.repo.BookRepository;
import aqlaam.version2.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTest {

    private BookRepository bookRepository;
    private BookMapper bookMapper;
    private UserRepository userRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookMapper = mock(BookMapper.class);
        userRepository = mock(UserRepository.class);
        bookService = new BookService(bookRepository, bookMapper, userRepository);
    }

    @Test
    void shouldReturnAllBooks() {
        // Given
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        books.add(book);
        BookDto bookDto = BookDto.builder()
                .title("Test Book")
                .author("Test Author")
                .category(Category.HORROR)
                .build();
        when(bookRepository.findAllBooksByDeletedIsFalse()).thenReturn(books);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        // When
        List<BookDto> result = bookService.getAllBooks();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookDto, result.get(0));
    }

    @Test
    void shouldThrowExceptionWhenBookNotFoundByTitle() {
        // Given
        String title = "test";
        when(bookRepository.findBookByTitle(title)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomNotFoundException.class, () -> bookService.getBookByTitle(title));
    }

    @Test
    void shouldReturnBooksByCategory() {
        // Given
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        books.add(book);
        BookDto bookDto = BookDto.builder()
                .title("Test Book")
                .author("Test Author")
                .category(Category.HORROR)
                .build();
        when(bookRepository.findBooksByCategory(Category.HORROR)).thenReturn(books);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        // When
        List<BookDto> result = bookService.getBooksByCategory(Category.HORROR);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookDto, result.get(0));
    }
}