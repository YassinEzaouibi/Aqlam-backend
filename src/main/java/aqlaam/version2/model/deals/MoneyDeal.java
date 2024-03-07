package aqlaam.version2.model.deals;

import aqlaam.version2.model.BookCollection;
import aqlaam.version2.model.deals.Deal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "money_deal")
public class MoneyDeal extends Deal {

    // User 1 gives money to user 2
    @Column(name = "second_user_given", nullable = false)
    private double amountGivenByTheSecondUser;

    // User 2 Books money to user 1
    @OneToOne
    private BookCollection collectionGivenByTheFirstUser;
}
