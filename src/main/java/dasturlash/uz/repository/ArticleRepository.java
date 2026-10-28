package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ArticleRepository extends CrudRepository<ArticleEntity, UUID> {
}
