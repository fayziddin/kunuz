package dasturlash.uz.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ArticleShortInfoMapper {
    // id(uuid),title,description,image(id,url),published_date
    UUID getId();
    String getTitle();
    String getDescription();
    String getImageId();
    LocalDateTime getPublishedDate();
}
