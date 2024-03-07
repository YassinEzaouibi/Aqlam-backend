package aqlaam.version2.model.deals;

import aqlaam.version2.model.BookCollection;
import aqlaam.version2.model.deals.Deal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "hybrid_deal")
public class HybridDeal extends Deal {

    @OneToOne
    private BookCollection collectionGivenByTheFirstUser;

    @OneToOne
    private BookCollection collectionGivenByTheSecondUser;

    @Column(name = "first_user_money", nullable = false)
    private double amountGivenByTheFirstUser;

    @Column(name = "second_user_money", nullable = false)
    private double amountGivenByTheSecondUser;
}
