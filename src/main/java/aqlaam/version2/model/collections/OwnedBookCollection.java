package aqlaam.version2.model.collections;

import aqlaam.version2.model.Book;
import aqlaam.version2.model.actors.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "owned_book_collection")
public class OwnedBookCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @JsonBackReference
    @OneToMany
    @JoinColumn(name = "book_id")
    private List<Book> books;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean deleted = false;
}
