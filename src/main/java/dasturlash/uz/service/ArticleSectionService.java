package dasturlash.uz.service;

import dasturlash.uz.entity.ArticleCategoryEntity;
import dasturlash.uz.entity.ArticleSectionEntity;
import dasturlash.uz.repository.ArticleSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleSectionService {
    private final ArticleSectionRepository repository;

    public void create(UUID articleId, Integer[] categoryIds){
        List<ArticleSectionEntity> entityList = new ArrayList<>();
        for(Integer id:categoryIds){
            ArticleSectionEntity entity = new ArticleSectionEntity();
            entity.setArticleId(articleId);
            entity.setSectionId(id);
            entityList.add(entity);
        }
        repository.saveAll(entityList);
    }

    public void create(UUID articleId, Integer sectionId){
        ArticleSectionEntity entity = new ArticleSectionEntity();
        entity.setArticleId(articleId);
        entity.setSectionId(sectionId);
        repository.save(entity);
    }

    public void deleteByArticleId(UUID articleId){
        repository.deleteByIdArticleId(articleId);
    }

    public void save(UUID articleId, Integer[] sectionIds){
        deleteByArticleId(articleId);
        create(articleId, sectionIds);
    }
}
