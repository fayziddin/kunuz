package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleTypeEntity;
import dasturlash.uz.mapper.ArticleTypeMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Long>, PagingAndSortingRepository<ArticleTypeEntity, Long> {

    Optional<ArticleTypeEntity> findByArticleTypeKey(String articleTypeKey);

    @Transactional
    @Modifying
    @Query("update ArticleTypeEntity set visible = false where id = ?1")
    int setVisibleById(Long id);

    Optional<ArticleTypeEntity> findByIdAndVisibleIsTrue(Long id);

    @Query("from ArticleTypeEntity where visible = true order by orderNumber asc")
    Page<ArticleTypeEntity> findAllVisibleSorted(Pageable pageRequest);

    @Query("select a.id as id, " +
            "case :lang " +
            "  when 'uz' then a.nameUz " +
            "  when 'ru' then a.nameRu " +
            "  when 'en' then a.nameEn " +
            "end as name, " +
            "a.articleTypeKey as articleTypeKey, " +
            "a.orderNumber as orderNumber " +
            "from ArticleTypeEntity a " +
            "where a.visible = true order by a.orderNumber asc")
    List<ArticleTypeMapper> getByLang(@Param("lang") String lang,
                                      Pageable pageRequest);
}
