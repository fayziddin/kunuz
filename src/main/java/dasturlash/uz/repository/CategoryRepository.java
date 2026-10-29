package dasturlash.uz.repository;

import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.mapper.CategoryMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByCategoryKey(String categoryKey);

    Optional<CategoryEntity> findByIdAndVisibleIsTrue(Long id);

    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible = false where id = ?1")
    int setVisibleById(Long id);

    @Query("from CategoryEntity where visible = true order by orderNumber asc")
    Iterable<CategoryEntity> findAllVisibleSorted();

    @Query("select c.id as id, " +
            "case :lang " +
            "  when 'uz' then c.nameUz " +
            "  when 'ru' then c.nameRu " +
            "  when 'en' then c.nameEn " +
            "end as name, " +
            "c.categoryKey as categoryKey," +
            "c.orderNumber as orderNumber " +
            "from CategoryEntity as c " +
            "where c.visible = true order by c.orderNumber asc")
    List<CategoryMapper> getAllByLang(@Param("lang") String lang);
}
