package aqlaam.version2.model.deals;

import aqlaam.version2.model.actors.Person;
import aqlaam.version2.model.enums.DealStatus;
import aqlaam.version2.model.enums.DealType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne
    private Person firstUser;

    @ManyToOne
    private Person secondUser;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DealStatus dealStatus;

    @Column(name = "deal_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DealType dealType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime finishedAt;

}
