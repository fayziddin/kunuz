package dasturlash.uz.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationConfirmDTO {
    private String username;
    private Integer code;
}
