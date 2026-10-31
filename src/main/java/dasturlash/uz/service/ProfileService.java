package dasturlash.uz.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.dto.ProfileFilterDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.repository.ProfileCustomRepository;
import dasturlash.uz.repository.ProfileRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repository;
    private final ProfileCustomRepository customRepository;
    private final ProfileRoleService profileRoleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public ProfileDTO create(ProfileDTO dto) {
        Optional<ProfileEntity> optionalByUsername = repository.findByUsername(dto.getUsername());
        if(optionalByUsername.isPresent()){
            throw new IllegalArgumentException("username is present");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setStatus(dto.getStatus());
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        // Save profile
        repository.save(entity);
        // Save role
        profileRoleService.save(dto.getId(), dto.getRoleList());

        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ProfileDTO update(Long id, ProfileDTO dto) {
        Optional<ProfileEntity> optionalByUsername = repository.findByUsername(dto.getUsername());
        if(optionalByUsername.isPresent() && !id.equals(optionalByUsername.get().getId())){
            throw new IllegalArgumentException("username is present");
        }
        Optional<ProfileEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Profile not found");
        }
        ProfileEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setStatus(dto.getStatus());
        // Save profile
        repository.save(entity);
        // Save role
        profileRoleService.save(id, dto.getRoleList());
        ProfileDTO response = toDto(entity);
        response.setRoleList(dto.getRoleList());
        return response;
    }

    public Page<ProfileDTO> getAllPagination(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));

        Page<ProfileEntity> pageResult = repository.findAll(pageRequest);
        Long totalItems = pageResult.getTotalElements();

        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : pageResult.getContent()) {
            dtoList.add(toDto(entity));
        }
        return new PageImpl<>(dtoList, pageRequest, totalItems);
    }

    private ProfileDTO toDto(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setStatus(entity.getStatus());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhotoId(entity.getPhotoId());

        return dto;
    }

    public Boolean deleteById(Long id) {
        Optional<ProfileEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if(optional.isEmpty()){
            return false;
        }
        ProfileEntity entity = optional.get();
        entity.setVisible(false);
        repository.save(entity);
        return true;
    }

    public Page<ProfileDTO> filter(ProfileFilterDTO filterDto, Integer page, Integer size) {
        Page<ProfileEntity> pageResult = customRepository.filter(filterDto, page, size);
        Long totalItems = pageResult.getTotalElements();

        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : pageResult.getContent()) {
            dtoList.add(toDto(entity));
        }
        return new PageImpl<>(dtoList, PageRequest.of(page, size), totalItems);
    }
}
