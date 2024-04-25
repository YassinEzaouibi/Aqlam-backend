package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.BookDto;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.BookMapper;
import aqlaam.version2.model.Book;
import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.enums.Category;
import aqlaam.version2.repo.BookRepository;
import aqlaam.version2.repo.UserRepository;
import aqlaam.version2.service.interfaces.IBookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;

    private static final String BOOK_NOT_FOUND = "Book not found";
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Override
    public List<BookDto> getAllBooks() {
        logger.info("Fetching all books");
        List<Book> books = bookRepository.findAllBooksByDeletedIsFalse();
        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto add(BookDto bookDto) {
        logger.info("Add a new book with title: {}", bookDto.getTitle());
        Optional<User> optionalUser = userRepository.findById(bookDto.getUserId());
        Optional<Book> optionalBook = bookRepository.findBookByReferenceAndDeletedIsFalse(bookDto.getReference());
        if (optionalBook.isPresent()) {
            throw new CustomNotFoundException("Book already exist with the same reference", HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.orElseThrow(
                () -> new CustomNotFoundException("User not found with this id: " + bookDto.getUserId(), HttpStatus.NOT_FOUND));

        Book bookEntity = bookMapper.toEntity(bookDto);
        bookEntity.setUser(user);
        Book savedBookEntity = bookRepository.save(bookEntity);
        logger.info("Book created with id: {}", savedBookEntity.getId());
        return bookMapper.toDto(savedBookEntity);
    }

    @Override
    public BookDto update(Long id, BookDto bookDto) {
        logger.info("Updating Book with id: {}", id);
        Book book = bookMapper.toEntity(bookDto);
        Optional<Book> optionalBook = bookRepository.findBookByDeletedIsFalseAndId(id);

        Book existingBook = optionalBook.orElseThrow(
                () -> new CustomNotFoundException(BOOK_NOT_FOUND, HttpStatus.NOT_FOUND));

        Optional<Book> bookOptional1 = bookRepository.findBookByReferenceAndDeletedIsFalse(book.getReference());
        if (bookOptional1.isPresent()) {
            throw new CustomNotFoundException("Book already exists with same reference" + bookDto.getReference(), HttpStatus.BAD_REQUEST);
        }
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPublicationDate(book.getPublicationDate());
        existingBook.setPages(book.getPages());
        existingBook.setDescription(book.getDescription());
        existingBook.setCover(book.getCover());
        existingBook.setLanguage(book.getLanguage());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setCategory(book.getCategory());
        existingBook.setBookPicture(book.getBookPicture());

        Book savedBook = bookRepository.save(existingBook);
        return bookMapper.toDto(savedBook);

    }

    @Override
    public BookDto getBookByTitle(String title) {
        logger.info("Fetching book with title: {}", title);
        Optional<Book> optionalBook = bookRepository.findBookByTitle(title);
        if (optionalBook.isEmpty()) {
            throw new CustomNotFoundException(BOOK_NOT_FOUND + " with title: " + title, HttpStatus.NOT_FOUND);
        }
        return bookMapper.toDto(optionalBook.get());
    }

    @Override
    public List<BookDto> getBooksByCategory(Category category) {
        logger.info("Fetching books with category: {}", category);
        List<Book> books = bookRepository.findBooksByCategory(category);
        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> getBooksByAuthor(String author) {
        logger.info("Fetching books with category: {}", author);
        List<Book> books = bookRepository.findBooksByAuthor(author);
        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> getAllBooksByIdUser(Long id) {
        logger.info("Fetching all books by user with id: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(
                () -> new CustomNotFoundException("User not found with this id: " + id, HttpStatus.NOT_FOUND));
        List<Book> books = bookRepository.findBooksByUser(user);
        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public void deleteByTitle(String title) {
        logger.info("Deleting book with title: {}", title);
        Optional<Book> optionalBook = bookRepository.findBookByTitle(title);
        if (optionalBook.isEmpty()) {
            throw new CustomNotFoundException(BOOK_NOT_FOUND + " with this title: " + title, HttpStatus.NOT_FOUND);
        }
        Book book = optionalBook.get();
        book.setDeleted(true);
        bookRepository.save(book);
        logger.info("Book deleted with title: {}", title);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting book with id: {}", id);
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            logger.error("Book not found with id: {}", id);
            throw new CustomNotFoundException(BOOK_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        Book book = optionalBook.get();
        book.setDeleted(true);
        bookRepository.save(book);
        logger.info("Book deleted with id: {}", id);
    }

}
