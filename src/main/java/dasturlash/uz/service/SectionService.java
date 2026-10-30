package dasturlash.uz.service;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.SectionEntity;
import dasturlash.uz.enums.Language;
import dasturlash.uz.mapper.SectionMapper;
import dasturlash.uz.repository.SectionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final SectionRepository repository;

    public SectionDTO create(@Valid SectionDTO dto) {
        Optional<SectionEntity> optional = repository.findBySectionKey(dto.getSectionKey());
        if(optional.isPresent()){
            throw new IllegalArgumentException("Key is present");
        }
        SectionEntity entity = new SectionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setSectionKey(dto.getSectionKey());
        repository.save(entity);

        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public SectionDTO update(Long id, @Valid SectionDTO dto) {
        Optional<SectionEntity> optionalByKey = repository.findBySectionKey(dto.getSectionKey());
        if(optionalByKey.isPresent() && !id.equals(optionalByKey.get().getId())){
            throw new IllegalArgumentException("Key is present");
        }

        Optional<SectionEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Section not found");
        }
        SectionEntity entity = optional.get();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setSectionKey(dto.getSectionKey());
        repository.save(entity);

        return toDto(entity);
    }

    public Boolean deleteById(Long id) {
        return repository.setVisibleById(id) == 1;
    }

    public Page<SectionDTO> getAllPagination(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "orderNumber"));
        Page<SectionEntity> pageResponse = repository.findAllVisibleSorted(pageRequest);

        Long totalItems = pageResponse.getTotalElements();
        List<SectionDTO> dtoList = new LinkedList<>();
        for (SectionEntity entity : pageResponse.getContent()) {
            dtoList.add(toDto(entity));
        }
        return new PageImpl<>(dtoList, pageRequest, totalItems);
    }

    private SectionDTO toDto(SectionEntity entity) {
        SectionDTO dto = new SectionDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setSectionKey(entity.getSectionKey());
        return dto;
    }

    public List<SectionMapper> getByLang(Language lang, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "orderNumber"));
        List<SectionMapper> shortInfoList = repository.getByLang(lang.name(), pageRequest);
        return shortInfoList;
    }
}
