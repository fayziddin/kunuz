package dasturlash.uz.repository;

import dasturlash.uz.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long>, PagingAndSortingRepository<CategoryEntity, Long> {
    Iterable<CategoryEntity> findAllByOrderByOrderNumberAsc();
}
