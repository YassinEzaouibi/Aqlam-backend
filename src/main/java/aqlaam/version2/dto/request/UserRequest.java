package aqlaam.version2.dto.request;

import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.enums.Sex;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link User}
 */
@Value
@Builder
public class UserRequest implements Serializable {
    @NotBlank(message = "First name cannot be blank")
    String firstName;

    @NotBlank(message = "Last name cannot be blank")
    String lastName;

    @Email(message = "Email should be valid")
    String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one digit, one lower case letter, one upper case letter, and one special character.")
    String password;

    @Past(message = "Date of birth should be in the past")
    Date dateOfBirth;

    @NotNull(message = "Sex cannot be null")
    Sex sex;

    @NotBlank(message = "Username cannot be blank")
    String userName;

    String profilePicture;
}