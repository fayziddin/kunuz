package dasturlash.uz.dto.auth;

import dasturlash.uz.enums.ProfileRoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AuthResponseDTO {
    private String name;
    private String surname;
    private String username;
    private List<ProfileRoleEnum> roleList;
    private String jwtToken;
    private String refreshToken;
}