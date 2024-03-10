package aqlaam.version2.model.actors;

import aqlaam.version2.model.BookCollection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends Account {

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "profile_picture", nullable = false)
    private String profilePicture;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<BookCollection> bookCollections;

    @JsonIgnore
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean deleted = false;


}
