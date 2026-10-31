package dasturlash.uz.service;

import dasturlash.uz.entity.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRoleEnum;
import dasturlash.uz.repository.ProfileRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileRoleService {
    private final ProfileRoleRepository repository;

    public void create(Long profileId, List<ProfileRoleEnum> roleList){
        List<ProfileRoleEntity> entityList = new ArrayList<>();
        roleList.forEach(role -> {
            ProfileRoleEntity entity = new ProfileRoleEntity();
            entity.setProfileId(profileId);
            entity.setRole(role);
            entityList.add(entity);
        });
        repository.saveAll(entityList);
    }

    public void create(Long profileId, ProfileRoleEnum profileRoleEnum){
        ProfileRoleEntity entity = new ProfileRoleEntity();
        entity.setProfileId(profileId);
        entity.setRole(profileRoleEnum);
        repository.save(entity);
    }

    public void deleteByProfileId(Long profileId){
        repository.deleteByProfileId(profileId);
    }

    public void save(Long profileId, List<ProfileRoleEnum> roleList){
        deleteByProfileId(profileId);
        create(profileId, roleList);
    }
}
