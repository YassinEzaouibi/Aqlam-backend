package aqlaam.version2.repo;

import aqlaam.version2.model.BookCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCollectionRepository extends JpaRepository<BookCollection, Long> {

}
