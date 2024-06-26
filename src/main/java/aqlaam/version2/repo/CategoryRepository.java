package aqlaam.version2.repo;

import aqlaam.version2.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Book, Long> {

}
