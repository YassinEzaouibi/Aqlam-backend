package aqlaam.version2.repo;

import aqlaam.version2.model.deals.MoneyDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyDealRepository extends JpaRepository<MoneyDeal, Long> {
}