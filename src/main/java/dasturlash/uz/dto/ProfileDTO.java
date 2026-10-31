package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dasturlash.uz.enums.ProfileRoleEnum;
import dasturlash.uz.enums.ProfileStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;

    @NotNull(message = "status is required")
    @Enumerated(EnumType.STRING)
    private ProfileStatusEnum status;

    @NotEmpty(message = "role_list is required")
    @JsonProperty("role_list")
    @Enumerated(EnumType.STRING)
    private List<ProfileRoleEnum> roleList;

    private Boolean visible;

    private LocalDateTime createdDate;

    private Long photoId;
}
