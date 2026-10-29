package dasturlash.uz.service;

import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.entity.ArticleTypeEntity;
import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.enums.ArticleStatus;
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
    private final CategoryService categoryService;
    private final ArticleTypeService articleTypeService;

    public ArticleDTO create(@Valid ArticleDTO dto) {
//        RegionEntity region = regionService.findById(dto.getRegionId());
//        CategoryEntity category = categoryService.findById(dto.getCategoryId());
//        List<ArticleTypeEntity> articleTypeEntities = articleTypeService.findByIdList(dto.getArticleType());

        RegionEntity region = new RegionEntity();
        region.setId(dto.getRegionId());

        CategoryEntity category = new CategoryEntity();
        category.setId(dto.getCategoryId());

        List<ArticleTypeEntity> articleTypeEntities = new LinkedList<>();
        for (Integer articleId : dto.getArticleType()){
            ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
            articleTypeEntity.setId(articleId.longValue());
            articleTypeEntities.add(articleTypeEntity);
        }

        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegion(region);
        entity.setCategory(category);
        entity.setStatus(ArticleStatus.NotPublished);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(Boolean.TRUE);
        entity.setArticleTypeSet(new HashSet<>(articleTypeEntities));

        repository.save(entity);

        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.getVisible());
        return dto;
    }

    //(title,description,content,shared_count,image_id, region_id,category_id)
    public Boolean update(UUID id, ArticleDTO dto) {
        Optional<ArticleEntity> optional = repository.findById(id);
        if(optional.isEmpty()){
            return false;
        }
        RegionEntity region = new RegionEntity();
        region.setId(dto.getRegionId());

        CategoryEntity category = new CategoryEntity();
        category.setId(dto.getCategoryId());

        ArticleEntity entity = optional.get();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setSharedCount(dto.getSharedCount());
        entity.setImageId(dto.getImageId());
        entity.setRegion(region);
        entity.setCategory(category);

        repository.save(entity);
        return true;
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
