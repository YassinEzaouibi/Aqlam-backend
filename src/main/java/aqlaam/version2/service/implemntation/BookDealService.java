package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.request.BookDealRequest;
import aqlaam.version2.dto.response.BookDealResponse;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.BookDealMapper;
import aqlaam.version2.model.collections.OwnedBookCollection;
import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.deals.BookDeal;
import aqlaam.version2.model.enums.DealStatus;
import aqlaam.version2.repo.BookDealRepository;
import aqlaam.version2.repo.UserRepository;
import aqlaam.version2.service.interfaces.IBookDealService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookDealService implements IBookDealService {

    private final BookDealRepository bookDealRepository;
    private final BookDealMapper bookDealMapper;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(BookDealService.class);
    private static final String CHECK_1_USER = "check the first user {}";
    private static final String CHECK_2_USER = "check the second user {}";


    @Override
    public BookDealResponse add(BookDealRequest bookDealRequest) {
        logger.info("Creating a new book deal this data (from add methode) : {}", bookDealRequest);

        logger.info("Validating the first user");
        User user1 = validateAndGetFirstUser(bookDealRequest);
        logger.info("after Validation ( first user ) {}", user1);


        logger.info("Validating the second user");
        User user2 = validateAndGetSecondUser(bookDealRequest);
        logger.info("after Validation ( second user ) {}", user2);

        logger.info("Validating the first collection");
        OwnedBookCollection ownedBookCollection1 = validateFirstUserBookCollection(user1, bookDealRequest);
        logger.info("Check the first collection is it available");
        validateBookCollectionAvailability(ownedBookCollection1);


        logger.info("Validating the second collection");
        OwnedBookCollection ownedBookCollection2 = validateSecondUserBookCollection(user2, bookDealRequest);
        logger.info("Check the second collection is it available");
        validateBookCollectionAvailability(ownedBookCollection2);

        BookDeal bookDeal = new BookDeal();
        bookDeal.setFirstUser(user1);
        bookDeal.setSecondUser(user2);
        bookDeal.setFirstUserCollection(ownedBookCollection1);
        bookDeal.setSecondUserCollection(ownedBookCollection2);
        bookDeal.setDescription(bookDealRequest.getDescription());
        bookDeal.setDealStatus(DealStatus.PENDING);
        bookDeal.setDealType(bookDealRequest.getDealType());

        logger.info("Saving the deal");
        BookDeal savedDeal = bookDealRepository.save(bookDeal);
        return bookDealMapper.toDto1(savedDeal);
    }

    @Override
    public List<BookDealResponse> getAllBookDeals() {
        logger.info("Fetching all Deals");
        List<BookDeal> deals = bookDealRepository.findAllByIsDeletedFalse();
        return deals.stream().map(bookDealMapper::toDto1)
                .toList();
    }

    @Override
    public BookDealResponse getBookDealById(Long id) {
        logger.info("Fetching the deal with id: {}", id);
        Optional<BookDeal> bookDeal = bookDealRepository.findById(id);
        if (bookDeal.isEmpty()) {
            throw new CustomNotFoundException("Deal with ID: " + id + " does not exist", HttpStatus.NOT_FOUND);
        }
        return bookDealMapper.toDto1(bookDeal.get());
    }

    @Override
    public void deleteDealById(Long id) {
        logger.info("Deleting deal with id: {}", id);
        Optional<BookDeal> optionalBookDeal = bookDealRepository.findByIdAndIsDeletedFalse(id);
        BookDeal bookDeal = optionalBookDeal.orElseThrow(
                () -> new CustomNotFoundException("Deal with ID: " + id + " does not exist", HttpStatus.NOT_FOUND)
        );
        bookDeal.setIsDeleted(true);
        bookDealRepository.save(bookDeal);
        logger.info("Deal deleted with id: {}", id);
    }

    @Override
    public BookDealResponse acceptDeal(Long id) {
        logger.info("Accepting the deal with id: {}", id);
        Optional<BookDeal> optionalBookDeal = bookDealRepository.findById(id);
        if (optionalBookDeal.isEmpty()) {
            throw new CustomNotFoundException("Deal with ID: " + id + " does not exist", HttpStatus.NOT_FOUND);
        }
        BookDeal bookDeal = optionalBookDeal.get();
        bookDeal.setDealStatus(DealStatus.ACCEPTED);
        BookDeal savedDeal = bookDealRepository.save(bookDeal);
        return bookDealMapper.toDto1(savedDeal);
    }

    @Override
    public BookDealResponse rejectDeal(Long id) {
        logger.info("Reject the deal with id: {}", id);
        Optional<BookDeal> optionalBookDeal = bookDealRepository.findById(id);
        if (optionalBookDeal.isEmpty()) {
            throw new CustomNotFoundException("Deal with ID: " + id + " does not exist", HttpStatus.NOT_FOUND);
        }
        BookDeal bookDeal = optionalBookDeal.get();
        bookDeal.setDealStatus(DealStatus.REJECTED);
        bookDeal.setIsDeleted(true);
        bookDealRepository.save(bookDeal);
        return bookDealMapper.toDto1(bookDeal);
    }

    @Override
    public List<BookDealResponse> getAllAcceptedDeals(Long userId) {
        logger.info("Fetching all accepted deals for user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomNotFoundException("User with ID: " + userId + " does not exist", HttpStatus.NOT_FOUND));
        List<BookDeal> acceptedDealsFirstUser = bookDealRepository.findAllByFirstUserIdAndDealStatus(user, DealStatus.ACCEPTED);
        List<BookDeal> acceptedDealsSecondUser = bookDealRepository.findAllBySecondUserIdAndDealStatus(user, DealStatus.ACCEPTED);
        acceptedDealsFirstUser.addAll(acceptedDealsSecondUser);
        return acceptedDealsFirstUser.stream().map(bookDealMapper::toDto1).toList();
    }


    @Override
    public List<BookDealResponse> getAllPendedDeals(Long userId) {
        logger.info("Fetching all pended deals for user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomNotFoundException("User with ID: " + userId + " does not exist", HttpStatus.NOT_FOUND));
        List<BookDeal> acceptedDealsFirstUser = bookDealRepository.findAllByFirstUserIdAndDealStatus(user, DealStatus.PENDING);
        List<BookDeal> acceptedDealsSecondUser = bookDealRepository.findAllBySecondUserIdAndDealStatus(user, DealStatus.PENDING);
        acceptedDealsFirstUser.addAll(acceptedDealsSecondUser);
        return acceptedDealsFirstUser.stream().map(bookDealMapper::toDto1).toList();
    }

    @Override
    public List<BookDealResponse> getAllRejectedDeals(Long userId) {
        logger.info("Fetching all rejected deals for user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomNotFoundException("User with ID: " + userId + " does not exist", HttpStatus.NOT_FOUND));
        List<BookDeal> rejectedDealsFirstUser = bookDealRepository.findAllByFirstUserIdAndDealStatus(user, DealStatus.REJECTED);
        List<BookDeal> rejectedDealsSecondUser = bookDealRepository.findAllBySecondUserIdAndDealStatus(user, DealStatus.REJECTED);
        rejectedDealsFirstUser.addAll(rejectedDealsSecondUser);
        return rejectedDealsFirstUser.stream().map(bookDealMapper::toDto1).toList();
    }

    private User validateAndGetFirstUser(BookDealRequest bookDealRequest) {
        logger.info(CHECK_1_USER, bookDealRequest.getFirstUserId());
        return userRepository.findUserByDeletedIsFalseAndId(bookDealRequest.getFirstUserId())
                .orElseThrow(
                        () -> new CustomNotFoundException(
                                "First user with ID: " + bookDealRequest.getFirstUserId() + " does not exist", HttpStatus.BAD_REQUEST
                        )
                );
    }

    private User validateAndGetSecondUser(BookDealRequest bookDealRequest) {
        logger.info(CHECK_2_USER, bookDealRequest.getSecondUserId());
        return userRepository.findUserByDeletedIsFalseAndId(bookDealRequest.getSecondUserId())
                .orElseThrow(
                        () -> new CustomNotFoundException(
                                "Second user with ID: " + bookDealRequest.getSecondUserId() + " does not exist", HttpStatus.BAD_REQUEST
                        )
                );
    }

    private OwnedBookCollection validateFirstUserBookCollection(User user, BookDealRequest bookDealRequest) {
        logger.info(CHECK_1_USER, bookDealRequest.getFirstUserCollectionId());
        Optional<OwnedBookCollection> bookCollection1 = user.getBooksCollections().stream()
                .filter(collection -> collection.getId().equals(bookDealRequest.getFirstUserCollectionId()))
                .findFirst();

        if (!bookCollection1.isPresent()) {
            throw new CustomNotFoundException(
                    "BookCollection with id: " + bookDealRequest.getFirstUserCollectionId() + " does not exist for the first user", HttpStatus.BAD_REQUEST
            );
        }
        return bookCollection1.get();
    }

    private OwnedBookCollection validateSecondUserBookCollection(User user, BookDealRequest bookDealRequest) {
        logger.info(CHECK_2_USER, bookDealRequest.getSecondUserCollectionId());
        Optional<OwnedBookCollection> bookCollection2 = user.getBooksCollections().stream()
                .filter(collection -> collection.getId().equals(bookDealRequest.getSecondUserCollectionId()))
                .findFirst();

        if (!bookCollection2.isPresent()) {
            throw new CustomNotFoundException(
                    "BookCollection with id: " + bookDealRequest.getSecondUserCollectionId() + " does not exist for the second user", HttpStatus.BAD_REQUEST
            );
        }
        return bookCollection2.get();
    }

    private void validateBookCollectionAvailability(OwnedBookCollection ownedBookCollection) {
        List<BookDeal> bookDeals = bookDealRepository.findAll();
        bookDeals.stream()
                .filter(deal -> !deal.getIsDeleted() && (deal.getFirstUserCollection().getId().equals(ownedBookCollection.getId()) || deal.getSecondUserCollection().getId().equals(ownedBookCollection.getId())) && (deal.getDealStatus() == DealStatus.PENDING || deal.getDealStatus() == DealStatus.ACCEPTED))
                .findFirst()
                .ifPresent(deal -> {
                    throw new CustomNotFoundException(
                            "BookCollection with id: " + ownedBookCollection.getId() + " is already in a deal", HttpStatus.BAD_REQUEST
                    );
                });
    }
}
