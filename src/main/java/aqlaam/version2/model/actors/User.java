package aqlaam.version2.model.actors;

import aqlaam.version2.model.Book;
import aqlaam.version2.model.BookCollection;
import aqlaam.version2.model.enums.AccountType;
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
    private List<BookCollection> booksCollections = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Book> books = new ArrayList<>();

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType = AccountType.USER;

    @JsonIgnore
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean deleted = false;
}
