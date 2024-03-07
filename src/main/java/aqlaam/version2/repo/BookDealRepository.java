package aqlaam.version2.repo;

import aqlaam.version2.model.deals.BookDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDealRepository extends JpaRepository<BookDeal, Long> {

}
