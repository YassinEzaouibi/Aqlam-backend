package aqlaam.version2.repo;

import aqlaam.version2.model.deals.HybridDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HybridDealRepository extends JpaRepository<HybridDeal, Long> {
}
