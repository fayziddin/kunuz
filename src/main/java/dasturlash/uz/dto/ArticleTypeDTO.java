package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleTypeDTO {
    @JsonProperty("order_number")
    private Integer orderNumber;

    @JsonProperty("name_uz")
    @NotBlank(message = "name_uz required")
    private String nameUz;

    @JsonProperty("name_ru")
    @NotBlank(message = "name_ru required")
    private String nameRu;

    @JsonProperty("name_en")
    @NotBlank(message = "name_en required")
    private String nameEn;
}
