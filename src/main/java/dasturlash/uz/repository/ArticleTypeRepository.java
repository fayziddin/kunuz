package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Long>, PagingAndSortingRepository<ArticleTypeEntity, Long> {
}
