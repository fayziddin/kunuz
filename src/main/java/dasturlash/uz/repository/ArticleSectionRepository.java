package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleSectionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleSectionRepository extends CrudRepository<ArticleSectionEntity, Long> {

    @Transactional
    @Modifying
    @Query("delete from ArticleSectionEntity where articleId = ?1")
    void deleteByIdArticleId(UUID articleId);
}
