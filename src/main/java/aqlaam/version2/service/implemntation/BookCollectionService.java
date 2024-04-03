package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.BookCollectionResponse;
import aqlaam.version2.dto.BookCollectionRequest;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.BookCollectionMapper;
import aqlaam.version2.model.Book;
import aqlaam.version2.model.BookCollection;
import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.enums.BookCollectionType;
import aqlaam.version2.repo.BookCollectionRepository;
import aqlaam.version2.repo.BookRepository;
import aqlaam.version2.repo.UserRepository;
import aqlaam.version2.service.interfaces.IBookCollectionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCollectionService implements IBookCollectionService {

    private final BookCollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookCollectionMapper collectionMapper;

    private final Logger logger = LoggerFactory.getLogger(BookCollectionService.class);
    private static final String COLLECTION_NOT_FOUND = "Book collection not found";

    @Override
    public List<BookCollectionResponse> getAllCollections() {
        logger.info("Fetching all book collections");
        List<BookCollection> bookCollections = collectionRepository.findAllByDeletedIsFalse();
        return bookCollections.stream()
                .map(collectionMapper::entityToResponse)
                .toList();
    }

    public BookCollectionResponse add(BookCollectionRequest request) {
        logger.info("Creating a new book collection this data (from add methode) : {}", request);

        // check if user exists on the database or not
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(
                        () -> new CustomNotFoundException(
                                "User with id: " + request.getUserId() + " does not exist", HttpStatus.BAD_REQUEST
                        )
                );
        logger.info("User Exist in the database with these information {}", user);

        // check if all books exist on the database or not
        List<Book> books = bookRepository.findAllById(request.getBookIds());
        if (books.size() != request.getBookIds().size()) {
            throw new CustomNotFoundException("One or more books do not exist", HttpStatus.BAD_REQUEST);
        }
        logger.info("Books Exist in the database with these information {}", books);

        // Check if any of the books already exist in another collection
        for (Book book : books) {
            if (!collectionRepository.findByBooksContaining(book).isEmpty()) {
                throw new CustomNotFoundException("Book with id: " + book.getId() + " already exists in another collection", HttpStatus.BAD_REQUEST);
            }
        }
        logger.info("Books are not in another collection in the database {}", books);

        BookCollection collection = new BookCollection();
        collection.setTitle(request.getTitle());
        collection.setDescription(request.getDescription());
        collection.setType(request.getType());
        collection.setUser(user);
        collection.setBooks(books);

        BookCollection savedCollection = collectionRepository.save(collection);

        return collectionMapper.entityToResponse(savedCollection);
    }

    @Override
    public BookCollectionResponse update(Long id, BookCollectionRequest collectionRequest) {

        logger.info("Updating book collection with id: {}", id);
        BookCollection bookCollection = collectionMapper.requestToEntity(collectionRequest);

        Optional<BookCollection> optionalBookCollection = collectionRepository.findByDeletedIsFalseAndId(id);
        BookCollection existingBookCollection = optionalBookCollection.orElseThrow(() -> {
            logger.error(COLLECTION_NOT_FOUND + " with id: {}", id);
            return new CustomNotFoundException(COLLECTION_NOT_FOUND, HttpStatus.NOT_FOUND);
        });

        // check if all books exist on the database or not
        List<Book> books = bookRepository.findAllById(collectionRequest.getBookIds());
        if (books.size() != collectionRequest.getBookIds().size()) {
            throw new CustomNotFoundException("One or more books do not exist", HttpStatus.BAD_REQUEST);
        }
        logger.info("Books Exist in the database with these information {}", books);

        // Check if any of the books already exist in another collection
        for (Book book : books) {
            List<BookCollection> collectionsWithBook = collectionRepository.findByBooksContaining(book);
            if (!collectionsWithBook.isEmpty() && collectionsWithBook.stream().noneMatch(bc -> bc.getId().equals(id))) {
                throw new CustomNotFoundException("Book with id: " + book.getId() + " already exists in another collection", HttpStatus.BAD_REQUEST);
            }
        }
        logger.info("Books are not in another collection in the database {}", books);

        existingBookCollection.setTitle(bookCollection.getTitle());
        existingBookCollection.setDescription(bookCollection.getDescription());
        existingBookCollection.setType(bookCollection.getType());
        existingBookCollection.setBooks(books); // set the books in the existing collection to the books from the request

        BookCollection savedBookCollection = collectionRepository.save(existingBookCollection);
        logger.info("Book collection updated with id: {}", savedBookCollection.getId());
        return collectionMapper.entityToResponse(savedBookCollection);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting book collection with id: {}", id);
        Optional<BookCollection> optionalBookCollection = collectionRepository.findByDeletedIsFalseAndId(id);
        BookCollection bookCollection = optionalBookCollection.orElseThrow(() -> {
            logger.error("Book collection not found with id: {}", id);
            return new CustomNotFoundException(COLLECTION_NOT_FOUND, HttpStatus.NOT_FOUND);
        });
        bookCollection.setDeleted(true);
        collectionRepository.save(bookCollection);
        logger.info("Book collection deleted with id: {}", id);
    }

    @Override
    public BookCollectionResponse getCollectionById(Long id) {
        logger.info("Fetching book collection with id: {}", id);
        BookCollection bookCollection = collectionRepository.findByDeletedIsFalseAndId(id)
                .orElseThrow(() -> {
                    logger.error("Book collection not found with id: {}", id);
                    return new CustomNotFoundException(COLLECTION_NOT_FOUND, HttpStatus.NOT_FOUND);
                });
        return collectionMapper.entityToResponse(bookCollection);
    }

    public BookCollectionResponse getCollectionByTitle(String title) {
        logger.info("Fetching book collection with title: {}", title);
        BookCollection bookCollection = collectionRepository.findByDeletedIsFalseAndTitle(title)
                .orElseThrow(() -> {
                    logger.error("Book collection not found with title: {}", title);
                    return new CustomNotFoundException(COLLECTION_NOT_FOUND, HttpStatus.NOT_FOUND);
                });
        return collectionMapper.entityToResponse(bookCollection);
    }

    public List<BookCollectionResponse> getCollectionByType(BookCollectionType type) {
        logger.info("Fetching book collection with type: {}", type);
        List<BookCollection> bookCollections = collectionRepository.findByDeletedIsFalseAndType(type);
        return bookCollections.stream()
                .map(collectionMapper::entityToResponse)
                .toList();
    }
}
