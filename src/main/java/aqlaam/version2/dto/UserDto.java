package aqlaam.version2.dto;

import aqlaam.version2.model.enums.Role;
import aqlaam.version2.model.enums.Sex;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * DTO for {@link User}
*/


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @Past(message = "Date of birth should be in the past")
    private Date dateOfBirth;

    private Sex sex;

    private Role role;

    @NotNull(message = "Profile picture cannot be null")
    private String profilePicture;

    @NotNull(message = "Book collections cannot be null")
    private List<Long> bookCollectionIds;


}

