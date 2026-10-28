package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dasturlash.uz.enums.ArticleStatus;
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
    private Long regionId;

    @NotNull(message = "Category is required")
    @JsonProperty("category_id")
    private Long categoryId;

    @NotEmpty(message = "Article type is required")
    @JsonProperty("article_type")
    private Integer[] articleType;

    private ArticleStatus status;

    private LocalDateTime createdDate;

    private Boolean visible;
}
