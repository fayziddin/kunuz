package dasturlash.uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "section")
public class SectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "name_uz", nullable = false, length = 100)
    private String nameUz;

    @Column(name = "name_ru", nullable = false, length = 100)
    private String nameRu;

    @Column(name = "name_en", nullable = false, length = 100)
    private String nameEn;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "section_key", nullable = false, unique = true)
    private String sectionKey;

    @ManyToMany(mappedBy = "sectionSet")
    private Set<ArticleEntity> articleSet;
}
