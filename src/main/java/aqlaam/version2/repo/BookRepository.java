package aqlaam.version2.repo;

import aqlaam.version2.model.Book;
import aqlaam.version2.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByAuthor(String author);
    List<Book> findBooksByCategory(Category category);
    List<Book> findBooksByUserId(Long id);
    void deleteBookByIdAndDeletedIsFalse(Long id);
    Optional<Book> findBookByTitle(String title);
    List<Book> findAllByUserId(Long id);
    Optional<Book> findBookByReferenceAndDeletedIsFalse(String reference);
    List<Book> findAllBooksByDeletedIsFalse();
    Optional<Book> findBookByDeletedIsFalseAndId(Long id);
}
