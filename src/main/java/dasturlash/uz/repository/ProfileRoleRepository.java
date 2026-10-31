package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRoleEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Long> {

    @Transactional
    @Modifying
    @Query("delete from ProfileRoleEntity as r where r.profileId = ?1 and r.role = ?2")
    void deleteByProfileAndRole(Long profileId, ProfileRoleEnum role);

    @Transactional
    @Modifying
    @Query("delete from ProfileRoleEntity as r where r.profileId = ?1")
    void deleteByProfileId(Long profileId);
}
