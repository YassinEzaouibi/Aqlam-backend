package aqlaam.version2.model.deals;

import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.enums.DealStatus;
import aqlaam.version2.model.enums.Role;
import aqlaam.version2.model.enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;


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
    private User firtsUser;

    @ManyToOne
    private User secondUser;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DealStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime finishedAt;

}
