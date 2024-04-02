package aqlaam.version2.model.deals;

import aqlaam.version2.model.BookCollection;
import aqlaam.version2.model.deals.Deal;
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
@Entity
@Table(name = "books_deal")
public class BookDeal extends Deal {

    @OneToMany
    private List<BookCollection> bookCollections;


}
