package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RegionDTO {
    private Long id;

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

    @NotBlank(message = "region_key is required")
    @JsonProperty("region_key")
    private String regionKey;

    private Boolean visible;

    private LocalDateTime createdDate;
}
