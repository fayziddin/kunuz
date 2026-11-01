package dasturlash.uz.entity;

import dasturlash.uz.enums.ArticleStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {
    // id(uuid),title,description,content,shared_count,image_id,
    //    region_id,moderator_id,publisher_id,status(Published,NotPublished),
    //    readTime (maqolani nechi daqiqa o'qilishi)
    //    created_date,published_date,visible,view_count
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount;

    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "region_id")
    private Integer regionId;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false, updatable = false, insertable = false)
    private RegionEntity region;

    @Column(name = "moderator_id")
    private Long moderatorId;

    @ManyToOne
    @JoinColumn(name = "moderator_id", updatable = false, insertable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Long publisherId;

    @ManyToOne
    @JoinColumn(name = "publisher_id", updatable = false, insertable = false)
    private ProfileEntity publisher;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatusEnum status;

    @Column(name = "read_time")
    @Positive
    private Integer readTime;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "visible", nullable = false)
    private Boolean visible;

    @Column(name = "view_count")
    private Integer viewCount;

    @OneToMany(mappedBy = "article")
    private List<ArticleCategoryEntity> categoryList;

    @OneToMany(mappedBy = "article")
    private List<ArticleSectionEntity> sectionList;
}
