package aqlaam.version2.model.deals;

import aqlaam.version2.model.collections.OwnedBookCollection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hybrid_deal")
public class HybridDeal extends Deal {

    @OneToMany
    private List<OwnedBookCollection> ownedBookCollections1;

    @OneToMany
    private List<OwnedBookCollection> ownedBookCollections2;

    @Column(name = "first_user_money", nullable = true)
    private double amountGivenByTheFirstUser;

    @Column(name = "second_user_money", nullable = true)
    private double amountGivenByTheSecondUser;

    @JsonIgnore
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

}
