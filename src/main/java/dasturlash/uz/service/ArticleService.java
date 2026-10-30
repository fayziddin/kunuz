package dasturlash.uz.service;

import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.entity.SectionEntity;
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
    private final SectionService sectionService;

    public ArticleDTO create(@Valid ArticleDTO dto) {
//        RegionEntity region = regionService.findById(dto.getRegionId());
//        CategoryEntity category = categoryService.findById(dto.getCategoryId());
//        List<SectionEntity> articleTypeEntities = sectionService.findByIdList(dto.getArticleType());

        RegionEntity region = new RegionEntity();
        region.setId(dto.getRegionId());

        CategoryEntity category = new CategoryEntity();
        category.setId(dto.getCategoryId());

        List<SectionEntity> sectionEntities = new LinkedList<>();
        for (Integer sectionId : dto.getSectionList()){
            SectionEntity sectionEntity = new SectionEntity();
            sectionEntity.setId(sectionId.longValue());
            sectionEntities.add(sectionEntity);
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
        entity.setSectionSet(new HashSet<>(sectionEntities));

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
