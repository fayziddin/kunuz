package dasturlash.uz.dto;

import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileDTO {
    @NotBlank(message = "name is required")
    @Size(min = 3, max = 80, message = "name length must be between 3 and 80")
    private String name;

    @NotBlank(message = "surname is required")
    @Size(min = 3, max = 80, message = "surname length must be between 3 and 80")
    private String surname;

    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^\\d{12}$", message = "phone must be 12 digits")
    private String phone;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "status is required")
    private ProfileStatus status;

    @NotBlank(message = "role is required")
    private ProfileRole role;
}
