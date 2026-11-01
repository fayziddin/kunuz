package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleCategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleCategoryRepository extends CrudRepository<ArticleCategoryEntity, Long> {

    @Transactional
    @Modifying
    @Query("delete from ArticleCategoryEntity where articleId = ?1")
    void deleteByIdArticleId(UUID articleId);
}
