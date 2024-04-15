package aqlaam.version2.model.actors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends Person{

    @JsonIgnore
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean deleted = false;

}
