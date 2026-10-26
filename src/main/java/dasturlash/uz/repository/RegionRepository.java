package dasturlash.uz.repository;

import dasturlash.uz.entity.RegionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Long> {
}
