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
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;
    private final RegionService regionService;
    private final CategoryService categoryService;
    private final ArticleTypeService articleTypeService;

    public ArticleDTO create(@Valid ArticleDTO dto) {
        RegionEntity region = regionService.findById(dto.getRegionId());
        CategoryEntity category = categoryService.findById(dto.getCategoryId());
        List<ArticleTypeEntity> articleTypeEntities = articleTypeService.findByIdList(dto.getArticleType());

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
}
