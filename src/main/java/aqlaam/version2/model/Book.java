package aqlaam.version2.model;

import aqlaam.version2.model.enums.BookCover;
import aqlaam.version2.model.enums.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publish_date", nullable = false)
    private Date publicationDate;

    @Column(name = "pages", nullable = false)
    private int pages;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cover", nullable = false)
    private BookCover cover;

    @Column(name = "language", nullable = false)
    private Language language;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @OneToOne
    private Category category;

    @Column(name = "book_picture", nullable = false)
    private String bookPicture;
}
