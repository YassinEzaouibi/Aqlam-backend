package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.BookCollectionResponse;
import aqlaam.version2.dto.BookCollectionRequest;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.BookCollectionMapper;
import aqlaam.version2.model.BookCollection;
import aqlaam.version2.repo.BookCollectionRepository;
import aqlaam.version2.repo.BookRepository;
import aqlaam.version2.service.interfaces.IBookCollectionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BCService implements IBookCollectionService {

    private final BookCollectionRepository collectionRepository;
    private final BookRepository bookRepository;
    private final BookCollectionMapper collectionMapper;

    private final Logger logger = LoggerFactory.getLogger(BCService.class);

    @Override
    public List<BookCollectionResponse> getAllCollections() {
        return null;
    }

//    @Override
//    public BCRequest add(BCRequest collectionRequest) {
//        logger.info("Creating a new book collection this data (from add methode) : {}", collectionRequest);
//        BookCollection bookCollection = collectionMapper.toEntity(collectionRequest);
//        logger.info("Creating a new book collection (after mapping) : {}", bookCollection.getTitle() + " " + bookCollection.getType() + " " + bookCollection.getUser() + " " + bookCollection.getBooks() + " " + bookCollection.getId());
//        logger.info("Creating a new book collection (after mapping) : {}", bookCollection);
//
//        Optional<BookCollection> bookCollectionOptional = collectionRepository.findByTitle(bookCollection.getTitle());
//        if (bookCollectionOptional.isPresent()) {
//            throw new CustomNotFoundException("Book collection already exists with same title", HttpStatus.BAD_REQUEST);
//        }
//
//        Long idUser = collectionRequest.getUserId();
//        List<Book> books = bookCollection.getBooks();
//        List<Book> bookIds = checkIfBookExists(idUser, books.stream().map(Book::getId).toList());
//        if (bookIds.isEmpty()) {
//            throw new CustomNotFoundException("One or more books do not exist", HttpStatus.BAD_REQUEST);
//        }
//
//        bookCollection.setBooks(books);
//        BookCollection savedBookCollection = collectionRepository.save(bookCollection);
//        return collectionMapper.toDto(savedBookCollection);
//    }

    public BookCollectionResponse add(BookCollectionRequest request) {
        // Convert DTO to entity
        logger.info("Before mapping (from the service) : {} ", request);
        BookCollection bookCollection = collectionMapper.requestToEntity(request);
        logger.info("After mapping (from the service) : {} ", request);

        // Save entity in the database
        BookCollection savedBookCollection = collectionRepository.save(bookCollection);

        // Convert entity back to DTO
        return collectionMapper.entityToResponse(savedBookCollection);
    }

    @Override
    public BookCollectionResponse update(Long id, BookCollectionRequest collectionRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public BookCollectionResponse getCollectionById(Long id) {
        BookCollection bookCollection = collectionRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Book collection with id: " + id + " does not exist", HttpStatus.BAD_REQUEST));
        return collectionMapper.entityToResponse(bookCollection);
    }

//    private List<Book> checkIfBookExists(Long idUser, List<Long> bookIds) {
//        List<Book> books = bookRepository.findBooksByUserId(idUser);
//        return bookIds.stream()
//                .map(bookId -> books.stream()
//                        .filter(book -> book.getId().equals(bookId))
//                        .findFirst()
//                        .orElseThrow(() -> new CustomNotFoundException("Book with id: " + bookId + " does not exist", HttpStatus.BAD_REQUEST)))
//                .toList();
//    }
}
