package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Long>, PagingAndSortingRepository<ArticleTypeEntity, Long> {
    List<ArticleTypeEntity> findAllByIdIn(Integer[] ids);
}
