package dasturlash.uz.service;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.CategoryShortInfo;
import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.dto.RegionShortInfo;
import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.enums.Language;
import dasturlash.uz.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryDTO create(@Valid CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();

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

    public Boolean update(CategoryDTO dto, Long id) {
        Optional<CategoryEntity> optional = repository.findById(id);
        if(optional.isEmpty()){
            return false;
        }
        CategoryEntity entity = optional.get();
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

    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> iterable = repository.findAllByOrderByOrderNumberAsc();
        List<CategoryDTO> dtoList = new LinkedList<>();
        for (CategoryEntity entity : iterable){
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    private CategoryDTO toDto(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<CategoryShortInfo> getByLang(Language lang) {
        Iterable<CategoryEntity> entityList = repository.findAll();
        List<CategoryShortInfo> shortInfoList = new LinkedList<>();
        for (CategoryEntity entity : entityList){
            CategoryShortInfo shortInfo = new CategoryShortInfo();
            shortInfo.setId(entity.getId());
            switch (lang){
                case uz -> {shortInfo.setName(entity.getNameUz());}
                case ru -> {shortInfo.setName(entity.getNameRu());}
                case en -> {shortInfo.setName(entity.getNameEn());}
            }
            shortInfo.setOrderNumber(entity.getOrderNumber());
            shortInfoList.add(shortInfo);
        }
        return shortInfoList;
    }

    public CategoryEntity findById(Long id){
        return repository.findById(id).orElse(null);
    }
}
