package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends CrudRepository<ArticleEntity, UUID> {
    Optional<ArticleEntity> findByIdAndVisibleIsTrue(UUID id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = false where id = ?1")
    int updateVisibleById(UUID id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set status = :status where id = :id and visible = true")
    int updateStatusById(@Param("id") UUID id, @Param("status") String status);
}
