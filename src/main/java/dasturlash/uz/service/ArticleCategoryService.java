package dasturlash.uz.service;

import dasturlash.uz.entity.ArticleCategoryEntity;
import dasturlash.uz.repository.ArticleCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleCategoryService {
    private final ArticleCategoryRepository repository;

    public void create(UUID articleId, Integer[] categoryIds){
        List<ArticleCategoryEntity> entityList = new ArrayList<>();
        for(Integer id:categoryIds){
            ArticleCategoryEntity entity = new ArticleCategoryEntity();
            entity.setArticleId(articleId);
            entity.setCategoryId(id);
            entityList.add(entity);
        }
        repository.saveAll(entityList);
    }

    public void create(UUID articleId, Integer categoryId){
        ArticleCategoryEntity entity = new ArticleCategoryEntity();
        entity.setArticleId(articleId);
        entity.setCategoryId(categoryId);
        repository.save(entity);
    }

    public void deleteByArticleId(UUID articleId){
        repository.deleteByIdArticleId(articleId);
    }

    public void save(UUID articleId, Integer[] categoryIds){
        deleteByArticleId(articleId);
        create(articleId, categoryIds);
    }
}
