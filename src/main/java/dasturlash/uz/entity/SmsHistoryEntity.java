package dasturlash.uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "to_phone")
    private String toPhone;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    @Column(name = "code")
    private String code;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;
}
