package aqlaam.version2.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class PersonLoginRequest {

    @Email(message = "Email should be valid")
    String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one digit, one lower case letter, one upper case letter, and one special character.")
    String password;
}
