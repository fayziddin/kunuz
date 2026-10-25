package dasturlash.uz.repository;

import dasturlash.uz.dto.ProfileFilterDTO;
import dasturlash.uz.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public Page<ProfileEntity> filter(ProfileFilterDTO filterDto, Integer page, Integer size) {
        StringBuilder selectQueryBuilder = new StringBuilder("Select p from ProfileEntity as p ");
        StringBuilder countQueryBuilder = new StringBuilder("Select count(p) from ProfileEntity as p ");

        StringBuilder conditionBuilder = new StringBuilder(" where p.visible = true ");

        Map<String, Object> params = new HashMap<>();
        if (filterDto.getName() != null) {
            conditionBuilder.append(" and p.name = :name");
            params.put("name", filterDto.getName());
        }

        if (filterDto.getSurname() != null) {
            conditionBuilder.append(" and p.surname = :surname");
            params.put("surname", filterDto.getSurname());
        }

        if (filterDto.getPhone() != null) {
            conditionBuilder.append(" and p.phone = :phone");
            params.put("phone", filterDto.getPhone());
        }

        if (filterDto.getRole() != null) {
            conditionBuilder.append(" and p.role = :role");
            params.put("role", filterDto.getRole());
        }

        if (filterDto.getCreated_date_from() != null) {
            conditionBuilder.append(" and p.createdDate > :fromDate");
            params.put("fromDate", filterDto.getCreated_date_from());
        }

        if (filterDto.getCreated_date_to() != null) {
            conditionBuilder.append(" and p.createdDate < :toDate");
            params.put("toDate", filterDto.getCreated_date_to());
        }

        selectQueryBuilder.append(conditionBuilder);
        countQueryBuilder.append(conditionBuilder);

        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());

        selectQuery.setFirstResult(page * size); // offset
        selectQuery.setMaxResults(size); // limit

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        List<ProfileEntity> entityList = selectQuery.getResultList();
        Long totalItems = (Long) countQuery.getSingleResult();

        return new PageImpl<>(entityList, PageRequest.of(page, size), totalItems);
    }
}
