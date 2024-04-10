package aqlaam.version2.repo;

import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.deals.HybridDeal;
import aqlaam.version2.model.deals.HybridDeal;
import aqlaam.version2.model.enums.DealStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HybridDealRepository extends JpaRepository<HybridDeal, Long> {

    List<HybridDeal> findAllByFirstUserIdAndDealStatus(User userId, DealStatus dealStatus);

    List<HybridDeal> findAllByIsDeletedFalse();

    Optional<HybridDeal> findByIdAndIsDeletedFalse(Long id);

    List<HybridDeal> findAllBySecondUserIdAndDealStatus(User userId, DealStatus dealStatus);

}
