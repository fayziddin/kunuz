package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryShortInfo {
    private Long id;
    @JsonProperty("order_number")
    private Integer orderNumber;
    private String name;
}
