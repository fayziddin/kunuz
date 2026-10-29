package dasturlash.uz.service;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.enums.Language;
import dasturlash.uz.mapper.CategoryMapper;
import dasturlash.uz.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        Optional<CategoryEntity> byKeyOptional = repository.findByCategoryKey(dto.getCategoryKey());
        if(byKeyOptional.isPresent()){
            throw new IllegalArgumentException("Key is present");
        }
        CategoryEntity entity = new CategoryEntity();

        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setCategoryKey(dto.getCategoryKey());
        repository.save(entity);

        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryDTO update(CategoryDTO dto, Long id) {
        Optional<CategoryEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if(optional.isEmpty()){
            throw new IllegalArgumentException("Category not found");
        }

        Optional<CategoryEntity> byKeyOptional = repository.findByCategoryKey(dto.getCategoryKey());
        if(byKeyOptional.isPresent() && !id.equals(byKeyOptional.get().getId())){
            throw new IllegalArgumentException("Key is present");
        }
        CategoryEntity entity = optional.get();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setCategoryKey(dto.getCategoryKey());
        repository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public Boolean delete(Long id) {
        return repository.setVisibleById(id) == 1;
    }

    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> iterable = repository.findAllVisibleSorted();
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
        dto.setCategoryKey(entity.getCategoryKey());
        return dto;
    }

    public List<CategoryMapper> getByLang(Language lang) {
        List<CategoryMapper> mapperList = repository.getAllByLang(lang.name());
        return mapperList;
    }
}
