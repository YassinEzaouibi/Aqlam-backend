package aqlaam.version2.dto;

import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.model.enums.Sex;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link aqlaam.version2.model.actors.Person}
 */
@Value
@Builder
public class PersonDto implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String email;
    String password;
    Date dateOfBirth;
    Sex sex;
    AccountType accountType;
}