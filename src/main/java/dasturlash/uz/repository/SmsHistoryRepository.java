package dasturlash.uz.repository;

import dasturlash.uz.entity.SmsHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, String> {
    @Query("from SmsHistoryEntity where toPhone = ?1 order by createdDate desc limit 1")
    Optional<SmsHistoryEntity> findLastByPhone(String phone);
}
