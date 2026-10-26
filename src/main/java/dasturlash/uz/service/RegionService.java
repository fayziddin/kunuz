package dasturlash.uz.service;

import dasturlash.uz.dto.ArticleTypeDTO;
import dasturlash.uz.dto.ArticleTypeShortInfo;
import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.dto.RegionShortInfo;
import dasturlash.uz.entity.ArticleTypeEntity;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.enums.Language;
import dasturlash.uz.repository.RegionRepository;
import jakarta.validation.Valid;
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
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());

        repository.save(entity);

        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(@Valid RegionDTO dto, Long id) {
        Optional<RegionEntity> optional = repository.findById(id);
        if(optional.isEmpty()){
            return false;
        }
        RegionEntity entity = optional.get();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        repository.save(entity);
        return true;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> iterable = repository.findAll();
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
        return dto;
    }

    public List<RegionShortInfo> getByLang(Language lang) {
        Iterable<RegionEntity> entityList = repository.findAll();
        List<RegionShortInfo> shortInfoList = new LinkedList<>();
        for (RegionEntity entity : entityList){
            RegionShortInfo shortInfo = new RegionShortInfo();
            shortInfo.setId(entity.getId());
            switch (lang){
                case uz -> {shortInfo.setName(entity.getNameUz());}
                case ru -> {shortInfo.setName(entity.getNameRu());}
                case en -> {shortInfo.setName(entity.getNameEn());}
            }
            shortInfo.setKey(entity.getOrderNumber());
            shortInfoList.add(shortInfo);
        }
        return shortInfoList;
    }
}
