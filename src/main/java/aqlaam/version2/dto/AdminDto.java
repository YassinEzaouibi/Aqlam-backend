package aqlaam.version2.dto;

import aqlaam.version2.model.actors.Admin;
import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.model.enums.Sex;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO for {@link Admin}
*/


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto {

    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 50, message = "First name must be between contains at least 2 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 50, message = "Last name must be between contains at least 2 characters")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least 8 characters, one digit, one lowercase, one uppercase and one special character")
    private String password;

    @Past(message = "Date of birth should be in the past")
    private Date dateOfBirth;

    @NotNull(message = "Gender is mandatory")
    private Sex sex;

    @NotNull(message = "Gender is mandatory")
    private AccountType accountType;

}
