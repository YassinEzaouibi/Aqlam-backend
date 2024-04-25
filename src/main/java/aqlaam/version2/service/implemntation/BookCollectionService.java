package aqlaam.version2.service.implemntation;


import aqlaam.version2.dto.request.OwnedBookCollectionRequest;
import aqlaam.version2.dto.response.OwnedBookCollectionResponse;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.OwnedBookCollectionMapper;
import aqlaam.version2.model.Book;
import aqlaam.version2.model.collections.OwnedBookCollection;
import aqlaam.version2.model.actors.User;
import aqlaam.version2.repo.BookCollectionRepository;
import aqlaam.version2.repo.BookRepository;
import aqlaam.version2.repo.UserRepository;
import aqlaam.version2.service.interfaces.IBookCollectionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCollectionService implements IBookCollectionService {

    private final BookCollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OwnedBookCollectionMapper collectionMapper;

    private final Logger logger = LoggerFactory.getLogger(BookCollectionService.class);
    private static final String COLLECTION_NOT_FOUND = "Book collection not found";

    @Override
    public List<OwnedBookCollectionResponse> getAllCollections() {
        logger.info("Fetching all book collections");
        List<OwnedBookCollection> ownedBookCollections = collectionRepository.findAllByDeletedIsFalse();
        return ownedBookCollections.stream()
                .map(collectionMapper::toDto1)
                .toList();
    }


    // add methode for type owned collection
    @Override
    public OwnedBookCollectionResponse addOwnedCollection(OwnedBookCollectionRequest request) {
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
        List<Book> userBooks = bookRepository.findAllByUserId(request.getUserId());

        // check if all books in the request are owned by the user
        List<Book> booksToAdd = new ArrayList<>();
        for (Long bookId : request.getBookIds()) {
            Book book = userBooks.stream()
                    .filter(b -> b.getId().equals(bookId))
                    .findFirst()
                    .orElseThrow(() -> new CustomNotFoundException("You do not own the book with id: " + bookId, HttpStatus.BAD_REQUEST));
            booksToAdd.add(book);
        }
        logger.info("All books in the request are owned by the user {}", user);

        // Check if any of the books already exist in another collection
        for (Book book : booksToAdd) {
            if (!collectionRepository.findByBooksContaining(book).isEmpty()) {
                throw new CustomNotFoundException("Book with id: " + book.getId() + " already exists in another collection", HttpStatus.BAD_REQUEST);
            }
        }
        logger.info("Books are not in another collection in the database {}", booksToAdd);

        OwnedBookCollection collection = new OwnedBookCollection();
        collection.setTitle(request.getTitle());
        collection.setDescription(request.getDescription());
        collection.setUser(user);
        collection.setBooks(booksToAdd);

        OwnedBookCollection savedCollection = collectionRepository.save(collection);

        return collectionMapper.toDto1(savedCollection);
    }

    @Override
    public OwnedBookCollectionResponse update(Long id, OwnedBookCollectionRequest collectionRequest) {

        logger.info("Updating book collection with id: {}", id);
        OwnedBookCollection ownedBookCollection = collectionMapper.toEntity(collectionRequest);

        Optional<OwnedBookCollection> optionalBookCollection = collectionRepository.findByDeletedIsFalseAndId(id);
        OwnedBookCollection existingOwnedBookCollection = optionalBookCollection.orElseThrow(() -> {
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
            List<OwnedBookCollection> collectionsWithBook = collectionRepository.findByBooksContaining(book);
            if (!collectionsWithBook.isEmpty() && collectionsWithBook.stream().noneMatch(bc -> bc.getId().equals(id))) {
                throw new CustomNotFoundException("Book with id: " + book.getId() + " already exists in another collection", HttpStatus.BAD_REQUEST);
            }
        }
        logger.info("Books are not in another collection in the database {}", books);

        existingOwnedBookCollection.setTitle(ownedBookCollection.getTitle());
        existingOwnedBookCollection.setDescription(ownedBookCollection.getDescription());
        existingOwnedBookCollection.setBooks(books); // set the books in the existing collection to the books from the request

        OwnedBookCollection savedOwnedBookCollection = collectionRepository.save(existingOwnedBookCollection);
        logger.info("Book collection updated with id: {}", savedOwnedBookCollection.getId());
        return collectionMapper.toDto1(savedOwnedBookCollection);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting book collection with id: {}", id);
        Optional<OwnedBookCollection> optionalBookCollection = collectionRepository.findByDeletedIsFalseAndId(id);
        OwnedBookCollection ownedBookCollection = optionalBookCollection.orElseThrow(() -> new CustomNotFoundException(COLLECTION_NOT_FOUND, HttpStatus.NOT_FOUND));
        ownedBookCollection.setDeleted(true);
        collectionRepository.save(ownedBookCollection);
        logger.info("Book collection deleted with id: {}", id);
    }

    @Override
    public OwnedBookCollectionResponse getCollectionById(Long id) {
        logger.info("Fetching book collection with id: {}", id);
        OwnedBookCollection ownedBookCollection = collectionRepository.findByDeletedIsFalseAndId(id)
                .orElseThrow(() -> new CustomNotFoundException(COLLECTION_NOT_FOUND, HttpStatus.NOT_FOUND));
        return collectionMapper.toDto1(ownedBookCollection);
    }


    @Override
    public OwnedBookCollectionResponse getCollectionByTitle(String title) {
        logger.info("Fetching book collection with title: {}", title);
        OwnedBookCollection ownedBookCollection = collectionRepository.findByDeletedIsFalseAndTitle(title)
                .orElseThrow(() -> {
                    logger.error("Book collection not found with title: {}", title);
                    return new CustomNotFoundException(COLLECTION_NOT_FOUND, HttpStatus.NOT_FOUND);
                });
        return collectionMapper.toDto1(ownedBookCollection);
    }

}
