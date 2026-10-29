package dasturlash.uz.service;

import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.enums.Language;
import dasturlash.uz.mapper.RegionMapper;
import dasturlash.uz.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository repository;

    public RegionDTO create(RegionDTO dto) {
        Optional<RegionEntity> optional = repository.findByRegionKey(dto.getRegionKey());
        if(optional.isPresent()){
            throw new IllegalArgumentException("Key is present");
        }
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setRegionKey(dto.getRegionKey());

        repository.save(entity);

        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public RegionDTO update(RegionDTO dto, Long id) {
        Optional<RegionEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if(optional.isEmpty()){
            throw new IllegalArgumentException("Region not found");
        }

        Optional<RegionEntity> optionalByKey = repository.findByRegionKey(dto.getRegionKey());
        if(optionalByKey.isPresent() && !id.equals(optionalByKey.get().getId())){
            throw new IllegalArgumentException("Key is present");
        }

        RegionEntity entity = optional.get();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setRegionKey(dto.getRegionKey());
        repository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean delete(Long id) {
        Optional<RegionEntity> optional = repository.findById(id);
        if(optional.isEmpty()){
            return false;
        }
        RegionEntity entity = optional.get();
        entity.setVisible(Boolean.FALSE);
        repository.save(entity);
        return true;
    }

    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> iterable = repository.findAllVisibleSorted();
        List<RegionDTO> dtoList = new LinkedList<>();
        for(RegionEntity entity : iterable){
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    private RegionDTO toDto(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setRegionKey(entity.getRegionKey());
        return dto;
    }

    public List<RegionMapper> getByLang(Language lang) {
        List<RegionMapper> mapperlist = repository.getByLang(lang.name());
        return mapperlist;
    }
}
