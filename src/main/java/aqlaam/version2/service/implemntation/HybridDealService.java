package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.request.HybridDealRequest;
import aqlaam.version2.dto.response.HybridDealResponse;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.HybridDealMapper;
import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.collections.OwnedBookCollection;
import aqlaam.version2.model.deals.HybridDeal;
import aqlaam.version2.model.enums.DealStatus;
import aqlaam.version2.repo.HybridDealRepository;
import aqlaam.version2.repo.UserRepository;
import aqlaam.version2.service.interfaces.IHybridDealService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HybridDealService implements IHybridDealService {

    private final HybridDealRepository dealRepository;
    private final HybridDealMapper hybridDealMapper;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(HybridDealService.class);
    public static final String DEAL_ID = "Deal with ID: ";
    public static final String NOT_EXIST = " does not exist";


    @Override
    public HybridDealResponse add(HybridDealRequest hybridDealRequest) {
        logger.info("Creating a new book deal this data (from add methode) : {}", hybridDealRequest);

        logger.info("Validating the first user");
        User user1 = validateAndGetUser(hybridDealRequest.getFirstUserIdId());
        logger.info("after Validation ( first user ) {}", user1);


        logger.info("Validating the second user");
        User user2 = validateAndGetUser(hybridDealRequest.getSecondUserIdId());
        logger.info("after Validation ( second user ) {}", user2);


        logger.info("Validating the first user book collection");
        validateUserBookCollections(user1, hybridDealRequest.getOwnedBookCollections1Ids());

        logger.info("Validating the second user book collection");
        validateUserBookCollections(user2, hybridDealRequest.getOwnedBookCollections2Ids());


        logger.info("Validating the first user book collection");
        validateUserBookCollections(user1, hybridDealRequest.getOwnedBookCollections1Ids());
        validateBookCollectionsAvailability(hybridDealRequest.getOwnedBookCollections1Ids());

        logger.info("Validating the second user book collection");
        validateUserBookCollections(user2, hybridDealRequest.getOwnedBookCollections2Ids());
        validateBookCollectionsAvailability(hybridDealRequest.getOwnedBookCollections2Ids());

        // retrieve the book collections
        List<OwnedBookCollection> ownedBookCollections1 = user1.getBooksCollections()
                .stream()
                .filter(collection -> hybridDealRequest.getOwnedBookCollections1Ids().contains(collection.getId()))
                .toList();
        List<OwnedBookCollection> ownedBookCollections2 = user2.getBooksCollections()
                .stream()
                .filter(collection -> hybridDealRequest.getOwnedBookCollections2Ids().contains(collection.getId()))
                .toList();


        HybridDeal hybridDeal = new HybridDeal();

        // set Users
        hybridDeal.setFirstUser(user1);
        hybridDeal.setSecondUser(user2);

        // set Description
        hybridDeal.setDescription(hybridDealRequest.getDescription());

        // set Amounts
        hybridDeal.setAmountGivenByTheFirstUser(hybridDealRequest.getAmountGivenByTheFirstUser());
        hybridDeal.setAmountGivenByTheSecondUser(hybridDealRequest.getAmountGivenByTheSecondUser());

        // set collections
        hybridDeal.setOwnedBookCollections1(ownedBookCollections1);
        hybridDeal.setOwnedBookCollections2(ownedBookCollections2);

        hybridDeal.setDealStatus(DealStatus.PENDING);
        hybridDeal.setDealType(hybridDealRequest.getDealType());

        logger.info("Saving the deal");
        HybridDeal hybridDeal1 = dealRepository.save(hybridDeal);
        return hybridDealMapper.toDto1(hybridDeal1);
    }

    @Override
    public List<HybridDealResponse> getAllHybridDeals() {
        logger.info("Fetching all Deals");
        List<HybridDeal> deals = dealRepository.findAllByIsDeletedFalse();
        return deals.stream().map(hybridDealMapper::toDto1)
                .toList();
    }

    @Override
    public HybridDealResponse getHybridDealById(Long id) {
        logger.info("Fetching the deal with id: {}", id);
        Optional<HybridDeal> bookDeal = dealRepository.findById(id);
        if (bookDeal.isEmpty()) {
            throw new CustomNotFoundException(DEAL_ID + id + NOT_EXIST, HttpStatus.NOT_FOUND);
        }
        return hybridDealMapper.toDto1(bookDeal.get());
    }

    @Override
    public void deleteDealById(Long id) {
        logger.info("Deleting deal with id: {}", id);
        Optional<HybridDeal> optionalBookDeal = dealRepository.findByIdAndIsDeletedFalse(id);
        HybridDeal bookDeal = optionalBookDeal.orElseThrow(
                () -> new CustomNotFoundException(DEAL_ID + id + NOT_EXIST, HttpStatus.NOT_FOUND)
        );
        bookDeal.setIsDeleted(true);
        dealRepository.save(bookDeal);
        logger.info("Deal deleted with id: {}", id);
    }

    @Override
    public HybridDealResponse acceptDeal(Long id) {
        logger.info("Accepting the deal with id: {}", id);
        Optional<HybridDeal> optionalBookDeal = dealRepository.findById(id);
        if (optionalBookDeal.isEmpty()) {
            throw new CustomNotFoundException(DEAL_ID + id + NOT_EXIST, HttpStatus.NOT_FOUND);
        }
        HybridDeal bookDeal = optionalBookDeal.get();
        bookDeal.setDealStatus(DealStatus.ACCEPTED);
        HybridDeal savedDeal = dealRepository.save(bookDeal);
        return hybridDealMapper.toDto1(savedDeal);
    }

    @Override
    public HybridDealResponse rejectDeal(Long id) {
        logger.info("Reject the deal with id: {}", id);
        Optional<HybridDeal> optionalBookDeal = dealRepository.findById(id);
        if (optionalBookDeal.isEmpty()) {
            throw new CustomNotFoundException(DEAL_ID + id + NOT_EXIST, HttpStatus.NOT_FOUND);
        }
        HybridDeal bookDeal = optionalBookDeal.get();
        bookDeal.setDealStatus(DealStatus.REJECTED);
        bookDeal.setIsDeleted(true);
        dealRepository.save(bookDeal);
        return hybridDealMapper.toDto1(bookDeal);
    }

    @Override
    public List<HybridDealResponse> getAllAcceptedDeals(Long userId) {
        logger.info("Fetching all accepted deals for user with id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomNotFoundException(DEAL_ID + userId + NOT_EXIST, HttpStatus.NOT_FOUND)
        );
        List<HybridDeal> acceptedDealsFirstUser = dealRepository.findAllByFirstUserIdAndDealStatus(user, DealStatus.ACCEPTED);
        List<HybridDeal> acceptedDealsSecondUser = dealRepository.findAllBySecondUserIdAndDealStatus(user, DealStatus.ACCEPTED);
        acceptedDealsFirstUser.addAll(acceptedDealsSecondUser);
        return acceptedDealsFirstUser.stream().map(hybridDealMapper::toDto1).toList();

    }

    @Override
    public List<HybridDealResponse> getAllRejectedDeals(Long userId) {
        logger.info("Fetching all rejected deals for user with id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomNotFoundException(DEAL_ID + userId + NOT_EXIST, HttpStatus.NOT_FOUND)
        );
        List<HybridDeal> acceptedDealsFirstUser = dealRepository.findAllByFirstUserIdAndDealStatus(user, DealStatus.REJECTED);
        List<HybridDeal> acceptedDealsSecondUser = dealRepository.findAllBySecondUserIdAndDealStatus(user, DealStatus.REJECTED);
        acceptedDealsFirstUser.addAll(acceptedDealsSecondUser);
        return acceptedDealsFirstUser.stream().map(hybridDealMapper::toDto1).toList();

    }

    @Override
    public List<HybridDealResponse> getAllPendedDeals(Long userId) {
        logger.info("Fetching all Pended deals for user with id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomNotFoundException(DEAL_ID + userId + NOT_EXIST, HttpStatus.NOT_FOUND)
        );
        List<HybridDeal> acceptedDealsFirstUser = dealRepository.findAllByFirstUserIdAndDealStatus(user, DealStatus.PENDING);
        List<HybridDeal> acceptedDealsSecondUser = dealRepository.findAllBySecondUserIdAndDealStatus(user, DealStatus.PENDING);
        acceptedDealsFirstUser.addAll(acceptedDealsSecondUser);
        return acceptedDealsFirstUser.stream().map(hybridDealMapper::toDto1).toList();
    }


    private User validateAndGetUser(Long userId) {
        logger.info("check the user {}", userId);
        return userRepository.findUserByDeletedIsFalseAndId(userId).orElseThrow(
                () -> new CustomNotFoundException(DEAL_ID + userId + NOT_EXIST, HttpStatus.NOT_FOUND)
        );
    }

    private void validateUserBookCollections(User user, List<Long> bookCollectionIds) {
        for (Long bookCollectionId : bookCollectionIds) {
            user.getBooksCollections()
                    .stream()
                    .filter(collection -> collection.getId().equals(bookCollectionId) && !collection.getDeleted())
                    .findFirst()
                    .orElseThrow(
                            () -> new CustomNotFoundException(
                                    "BookCollection with id: " + bookCollectionId + " does not exist for the user or is deleted",
                                    HttpStatus.BAD_REQUEST
                            )
                    );
        }
    }

    private void validateBookCollectionsAvailability(List<Long> bookCollectionIds) {
        List<HybridDeal> hybridDeals = dealRepository.findAll();
        for (Long bookCollectionId : bookCollectionIds) {
            hybridDeals.stream().filter(deal -> !deal.getIsDeleted() && (deal.getOwnedBookCollections1().stream().anyMatch(collection -> collection.getId().equals(bookCollectionId)) || deal.getOwnedBookCollections2().stream().anyMatch(collection -> collection.getId().equals(bookCollectionId))) && (deal.getDealStatus() == DealStatus.PENDING || deal.getDealStatus() == DealStatus.ACCEPTED)).findFirst().ifPresent(deal -> {
                throw new CustomNotFoundException("BookCollection with id: " + bookCollectionId + " is already in a deal", HttpStatus.BAD_REQUEST);
            });
        }
    }


}
