package aqlaam.version2.repo;

import aqlaam.version2.model.Book;
import aqlaam.version2.model.collections.OwnedBookCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCollectionRepository extends JpaRepository<OwnedBookCollection, Long> {

    Optional<OwnedBookCollection> findByDeletedIsFalseAndTitle(String title);
    List<OwnedBookCollection> findAllByDeletedIsFalse();
    Optional<OwnedBookCollection> findByDeletedIsFalseAndId(Long id);
    List<OwnedBookCollection> findByBooksContaining(Book book);

}
