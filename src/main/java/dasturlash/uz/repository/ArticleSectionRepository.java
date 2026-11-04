package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleSectionEntity;
import dasturlash.uz.mapper.ArticleShortInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleSectionRepository extends CrudRepository<ArticleSectionEntity, Long>, PagingAndSortingRepository<ArticleSectionEntity, Long> {

    @Transactional
    @Modifying
    @Query("delete from ArticleSectionEntity where articleId = ?1")
    void deleteByIdArticleId(UUID articleId);

    @Query("select a.id as id, " +
            "a.title as title, " +
            "a.description as description, " +
            "a.imageId as imageId, " +
            "a.publishedDate as publishedDate " +
            "from ArticleSectionEntity as s inner join ArticleEntity as a " +
            "where s.sectionId = :sectionId")
    Page<ArticleShortInfoMapper> findBySectionId(@Param("sectionId") Integer sectionId, PageRequest pageRequest);
}
