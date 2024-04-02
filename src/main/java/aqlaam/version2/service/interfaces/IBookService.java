package aqlaam.version2.service.interfaces;

import aqlaam.version2.dto.BookDto;
import aqlaam.version2.model.enums.Category;

import java.util.List;

public interface IBookService {

    List<BookDto> getAllBooks();
    BookDto add(BookDto bookDto);
    BookDto update(Long id, BookDto bookDto);
    BookDto getBookByTitle(String title);
    List<BookDto> getBooksByCategory(Category category);
    void deleteByTitle(String title);
    void deleteById(Long id);
    List<BookDto> getBooksByAuthor(String author);

}
