package aqlaam.version2.dto;

import aqlaam.version2.model.BookCollection;
import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.model.enums.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * DTO for {@link User}
*/


@Data
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 15 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
    private String userName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,12}$",
            message = "Password must contain at least 8 characters, one digit, one lowercase, one uppercase and one special character"
    )
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth should be in the past")
    @NotNull
    private Date dateOfBirth;

    @NotNull(message = "Sex cannot be blank")
    private Sex sex;

    @NotNull(message = "Account type cannot be blank")
    private AccountType accountType;

    private String profilePicture;

    private List<BookCollection> bookCollections;


}

