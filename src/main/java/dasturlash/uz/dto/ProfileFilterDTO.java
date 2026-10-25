package dasturlash.uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String phone;
    private String role;
    private LocalDateTime created_date_from;
    private LocalDateTime created_date_to;
}
