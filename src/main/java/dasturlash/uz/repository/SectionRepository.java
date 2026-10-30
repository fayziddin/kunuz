package dasturlash.uz.repository;

import dasturlash.uz.entity.SectionEntity;
import dasturlash.uz.mapper.SectionMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends CrudRepository<SectionEntity, Long>, PagingAndSortingRepository<SectionEntity, Long> {

    Optional<SectionEntity> findBySectionKey(String sectionKey);

    @Transactional
    @Modifying
    @Query("update SectionEntity set visible = false where id = ?1")
    int setVisibleById(Long id);

    Optional<SectionEntity> findByIdAndVisibleIsTrue(Long id);

    @Query("from SectionEntity where visible = true order by orderNumber asc")
    Page<SectionEntity> findAllVisibleSorted(Pageable pageRequest);

    @Query("select a.id as id, " +
            "case :lang " +
            "  when 'uz' then a.nameUz " +
            "  when 'ru' then a.nameRu " +
            "  when 'en' then a.nameEn " +
            "end as name, " +
            "a.sectionKey as sectionKey, " +
            "a.orderNumber as orderNumber " +
            "from SectionEntity a " +
            "where a.visible = true order by a.orderNumber asc")
    List<SectionMapper> getByLang(@Param("lang") String lang,
                                  Pageable pageRequest);
}
