package dasturlash.uz.service;

import dasturlash.uz.dto.ArticleTypeDTO;
import dasturlash.uz.entity.ArticleTypeEntity;
import dasturlash.uz.enums.Language;
import dasturlash.uz.mapper.ArticleTypeMapper;
import dasturlash.uz.repository.ArticleTypeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleTypeService {
    private final ArticleTypeRepository repository;

    public ArticleTypeDTO create(@Valid ArticleTypeDTO dto) {
        Optional<ArticleTypeEntity> optional = repository.findByArticleTypeKey(dto.getArticleTypeKey());
        if(optional.isPresent()){
            throw new IllegalArgumentException("Key is present");
        }
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setArticleTypeKey(dto.getArticleTypeKey());
        repository.save(entity);

        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ArticleTypeDTO update(Long id, @Valid ArticleTypeDTO dto) {
        Optional<ArticleTypeEntity> optionalByKey = repository.findByArticleTypeKey(dto.getArticleTypeKey());
        if(optionalByKey.isPresent() && !id.equals(optionalByKey.get().getId())){
            throw new IllegalArgumentException("Key is present");
        }

        Optional<ArticleTypeEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Article type not found");
        }
        ArticleTypeEntity entity = optional.get();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setArticleTypeKey(dto.getArticleTypeKey());
        repository.save(entity);

        return toDto(entity);
    }

    public Boolean deleteById(Long id) {
        return repository.setVisibleById(id) == 1;
    }

    public Page<ArticleTypeDTO> getAllPagination(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ArticleTypeEntity> pageResponse = repository.findAllVisibleSorted(pageRequest);

        Long totalItems = pageResponse.getTotalElements();
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        for (ArticleTypeEntity entity : pageResponse.getContent()) {
            dtoList.add(toDto(entity));
        }
        return new PageImpl<>(dtoList, pageRequest, totalItems);
    }

    private ArticleTypeDTO toDto(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setArticleTypeKey(entity.getArticleTypeKey());
        return dto;
    }

    public List<ArticleTypeMapper> getByLang(Language lang, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ArticleTypeMapper> shortInfoList = repository.getByLang(lang.name(), pageRequest);
        return shortInfoList;
    }
}
