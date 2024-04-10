package aqlaam.version2.repo;

import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.deals.BookDeal;
import aqlaam.version2.model.enums.DealStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDealRepository extends JpaRepository<BookDeal, Long> {

    List<BookDeal> findAllByFirstUserIdAndDealStatus(User userId, DealStatus dealStatus);

    List<BookDeal> findAllByIsDeletedFalse();

    Optional<BookDeal> findByIdAndIsDeletedFalse(Long id);

    List<BookDeal> findAllBySecondUserIdAndDealStatus(User userId, DealStatus dealStatus);

}
