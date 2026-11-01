package dasturlash.uz.dto;

import dasturlash.uz.enums.ArticleStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleStatusDTO {
    @NotNull(message = "Status is required")
    private ArticleStatusEnum status;
}
