package aqlaam.version2.model.actors;

import aqlaam.version2.model.BookCollection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
public class User extends Account {

    @Column(name = "profile_picture", nullable = false)
    private String profilePicture;

    @OneToMany(mappedBy = "user")
    private List<BookCollection> bookCollections;


}
