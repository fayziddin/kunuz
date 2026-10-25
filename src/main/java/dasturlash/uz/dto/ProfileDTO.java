package dasturlash.uz.dto;

import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ProfileDTO {
    private Long id;

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

    @NotNull(message = "status is required")
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @NotNull(message = "role is required")
    @Enumerated(EnumType.STRING)
    private ProfileRole role;

    private Boolean visible;

    private LocalDateTime createdDate;

    private Long photoId;
}
