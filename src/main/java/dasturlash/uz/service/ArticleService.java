package dasturlash.uz.service;

import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.dto.ArticleStatusDTO;
import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.enums.ArticleStatusEnum;
import dasturlash.uz.repository.ArticleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;
    private final RegionService regionService;
    private final ArticleCategoryService articleCategoryService;
    private final ArticleSectionService articleSectionService;

    public ArticleDTO create(ArticleDTO dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setStatus(ArticleStatusEnum.NotPublished);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(Boolean.TRUE);
        // Save article
        repository.save(entity);
        // Save categories
        articleCategoryService.save(entity.getId(), dto.getCategoryList());
        // Save sections
        articleSectionService.save(entity.getId(), dto.getSectionList());
        dto.setId(entity.getId());
        return dto;
    }

    //(title,description,content,shared_count,image_id, region_id,category_id)
    public ArticleDTO update(UUID id, ArticleDTO dto) {
        Optional<ArticleEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if(optional.isEmpty()){
            throw new IllegalArgumentException("Article not found");
        }
        ArticleEntity entity = optional.get();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setStatus(ArticleStatusEnum.NotPublished);
        // Save article
        repository.save(entity);
        // Save categories
        articleCategoryService.save(entity.getId(), dto.getCategoryList());
        // Save sections
        articleSectionService.save(entity.getId(), dto.getSectionList());
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean delete(UUID id) {
        return repository.updateVisibleById(id) == 1;
    }

    public Boolean changeStatus(UUID id, ArticleStatusDTO request) {
        return repository.updateStatusById(id, request.getStatus().name()) == 1;
    }
}
