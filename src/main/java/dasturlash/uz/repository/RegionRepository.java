package dasturlash.uz.repository;

import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.mapper.RegionMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Long> {
    Optional<RegionEntity> findByRegionKey(String regionKey);

    Optional<RegionEntity> findByIdAndVisibleIsTrue(Long id);

    @Query("select r.id as id, " +
            "case :lang " +
            "  when 'uz' then r.nameUz " +
            "  when 'ru' then r.nameRu " +
            "  when 'en' then r.nameEn " +
            "end as name, " +
            "r.regionKey as regionKey, " +
            "r.orderNumber as orderNumber " +
            "from RegionEntity as r " +
            "where r.visible = true order by r.orderNumber asc")
    List<RegionMapper> getByLang(@Param("lang") String lang);

    @Query("from RegionEntity as r where r.visible = true order by r.orderNumber asc")
    Iterable<RegionEntity> findAllVisibleSorted();
}
