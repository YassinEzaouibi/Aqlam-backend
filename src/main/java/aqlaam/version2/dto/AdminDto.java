package aqlaam.version2.dto;

import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.model.enums.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link aqlaam.version2.model.actors.Admin}
 */
@Builder
@Value
public class AdminDto implements Serializable{

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 15 characters")
    String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    String lastName;


    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,12}$",
            message = "Password must contain at least 8 characters, one digit, one lowercase, one uppercase and one special character"
    )
    String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth should be in the past")
    @NotNull
    Date dateOfBirth;

    @NotNull(message = "Sex cannot be blank")
    Sex sex;

    @NotNull(message = "Account type cannot be blank")
    AccountType accountType;
}