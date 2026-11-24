package dasturlash.uz.dto.auth;

import dasturlash.uz.enums.ProfileRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtDTO {
    private String id;
    private String username;
    private List<ProfileRoleEnum> roles;
}
