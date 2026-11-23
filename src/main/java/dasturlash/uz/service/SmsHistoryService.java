package dasturlash.uz.service;

import dasturlash.uz.entity.SmsHistoryEntity;
import dasturlash.uz.repository.SmsHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SmsHistoryService {
    private final SmsHistoryRepository repository;

    public void create(String body, String code, String toPhone){
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setToPhone(toPhone);
        entity.setCode(code);
        entity.setBody(body);
        entity.setCreatedDate(LocalDateTime.now());
        repository.save(entity);
    }

    public boolean isSmsSendToPhone(String phone, int code) {
        Optional<SmsHistoryEntity> optional = repository.findLastByPhone(phone);
        if (optional.isEmpty()) {
            return false;
        }
        SmsHistoryEntity entity = optional.get();
        if (!entity.getCode().equals(String.valueOf(code))) {
            return false;
        }
        //   20:32:40          =   20:30.40  + 0:2:00
        LocalDateTime extDate = entity.getCreatedDate().plusMinutes(2);
        // now  20:31:30  >  20:32:40    |     now 20:35:30  >  20:32:40
        if (LocalDateTime.now().isAfter(extDate)) {
            return false;
        }
        return true;
    }
}
