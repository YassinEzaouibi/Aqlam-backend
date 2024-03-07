package aqlaam.version2.model;

import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.enums.BookCollectionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookCollection")
public class BookCollection {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookCollectionType type;

    @OneToMany
    private List<Book> books;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
