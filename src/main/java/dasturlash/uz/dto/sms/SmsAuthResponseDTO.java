package dasturlash.uz.dto.sms;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SmsAuthResponseDTO {
    private String id;
    private String jwt;
    private String login;
    private String name;
    private String surname;
}
