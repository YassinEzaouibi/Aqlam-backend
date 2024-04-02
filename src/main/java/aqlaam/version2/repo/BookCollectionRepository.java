package aqlaam.version2.repo;

import aqlaam.version2.model.BookCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCollectionRepository extends JpaRepository<BookCollection, Long> {

    Optional<BookCollection> findByTitle(String title);

    /*List<BookCollectionResponseDto> findBookCollectionsByType(BookCollectionType type);
    List<BookCollectionResponseDto> findAllBookCollectionsByeDeletedIsFalse();
    Optional<BookCollectionResponseDto> findBookCollectionsByIdAndDeletedIsFalse(Long id);*/
}
