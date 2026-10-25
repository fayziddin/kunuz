package dasturlash.uz.dto;

import dasturlash.uz.enums.Language;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ArticleTypeShortInfo {
    private Long id;
    private Language key;
    private String name;
}
