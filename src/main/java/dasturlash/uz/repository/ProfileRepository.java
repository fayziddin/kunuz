package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Long>, PagingAndSortingRepository<ProfileEntity, Long> {
    Optional<ProfileEntity> findByUsername(String username);

    Optional<ProfileEntity> findByIdAndVisibleIsTrue(Long id);
}
