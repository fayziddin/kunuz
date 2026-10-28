package dasturlash.uz.service;

import dasturlash.uz.dto.ArticleTypeDTO;
import dasturlash.uz.entity.ArticleTypeEntity;
import dasturlash.uz.enums.Language;
import dasturlash.uz.dto.ArticleTypeShortInfo;
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
        ArticleTypeEntity entity = new ArticleTypeEntity();
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

    public Boolean update(Long id, @Valid ArticleTypeDTO dto) {
        Optional<ArticleTypeEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        ArticleTypeEntity entity = optional.get();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        repository.save(entity);

        return true;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Page<ArticleTypeDTO> getAllPagination(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ArticleTypeEntity> pageResponse = repository.findAll(pageRequest);

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
        return dto;
    }

    public List<ArticleTypeShortInfo> getByLang(Language lang) {
        Iterable<ArticleTypeEntity> entityList = repository.findAll();
        List<ArticleTypeShortInfo> shortInfoList = new LinkedList<>();
        for (ArticleTypeEntity entity : entityList){
            ArticleTypeShortInfo shortInfo = new ArticleTypeShortInfo();
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

    public List<ArticleTypeEntity> findByIdList(Integer[] ids){
        return repository.findAllByIdIn(ids);
    }
}
