package aqlaam.version2.model.actors;

import aqlaam.version2.model.Book;
import aqlaam.version2.model.collections.FavoriteBookCollection;
import aqlaam.version2.model.collections.OwnedBookCollection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends Person {

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "profile_picture", nullable = false)
    private String profilePicture;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<OwnedBookCollection> booksCollections = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<FavoriteBookCollection> favoriteBookCollections = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Book> books = new ArrayList<>();

    @JsonIgnore
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean deleted = false;
}
