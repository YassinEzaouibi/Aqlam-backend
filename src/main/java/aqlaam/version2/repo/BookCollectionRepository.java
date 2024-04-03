package aqlaam.version2.repo;

import aqlaam.version2.model.Book;
import aqlaam.version2.model.BookCollection;
import aqlaam.version2.model.enums.BookCollectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCollectionRepository extends JpaRepository<BookCollection, Long> {

    Optional<BookCollection> findByDeletedIsFalseAndTitle(String title);
    List<BookCollection> findByDeletedIsFalseAndType(BookCollectionType type);
    List<BookCollection> findAllByDeletedIsFalse();
    Optional<BookCollection> findByDeletedIsFalseAndId(Long id);
    List<BookCollection> findByBooksContaining(Book book);

}
