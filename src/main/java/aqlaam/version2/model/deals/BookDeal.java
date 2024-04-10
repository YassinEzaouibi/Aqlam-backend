package aqlaam.version2.model.deals;

import aqlaam.version2.model.collections.OwnedBookCollection;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books_deal")
public class BookDeal extends Deal {

    @JsonBackReference
    @OneToOne
//    @JoinColumn(name = "first_user_id")
    private OwnedBookCollection firstUserCollection;

    @JsonBackReference
    @OneToOne
//    @JoinColumn(name = "second_user_id")
    private OwnedBookCollection secondUserCollection;

    @JsonIgnore
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

}
