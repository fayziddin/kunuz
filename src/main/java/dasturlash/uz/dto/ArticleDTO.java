package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dasturlash.uz.enums.ArticleStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class ArticleDTO {
    // title,description,content,imageId, regionId, categoryList[], sectionList[]
    private UUID id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Content is required")
    private String content;

    @JsonProperty("image_id")
    private Long imageId;

    @NotNull(message = "Region is required")
    @JsonProperty("region_id")
    private Integer regionId;

    @NotNull(message = "category_list is required")
    @JsonProperty("category_list")
    private Integer[] categoryList;

    @NotEmpty(message = "section_list is required")
    @JsonProperty("section_list")
    private Integer[] sectionList;
}
