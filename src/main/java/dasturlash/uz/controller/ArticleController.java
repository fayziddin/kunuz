package dasturlash.uz.controller;

import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService service;
    /*
    5. Get Last 5 Article By Types  ordered_by_created_date
        (Berilgan types bo'yicha oxirgi 5ta pubished bo'lgan article ni return qiladi.)
        ArticleShortInfo
    6. Get Last 3 Article By Types  ordered_by_created_date
        (Berilgan types bo'yicha oxirgi 3ta pubished bo'lgan article ni return qiladi.)
         ArticleShortInfo
    7. Get Last 8  Articles witch id not included in given list.
        ([1,2,3,])
        ArticleShortInfo
    8. Get Article By Id And Lang
        ArticleFullInfo
    9. Get Last 4 Article By Types and except given article id.
        ArticleShortInfo
    10. Get 4 most read articles
        ArticleShortInfo
     */

    // 1. CREATE (Moderator) status(NotPublished)
    //     (title,description,content,image_id, region_id,category_id, articleType(array))

    @PostMapping("")
    public ResponseEntity<ArticleDTO> create(@Valid @RequestBody ArticleDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    // 2. Update (Moderator (status to not publish)) (remove old image)
    //     (title,description,content,shared_count,image_id, region_id,category_id)
    @PostMapping("/{id}")
    public ResponseEntity<Boolean> update(@Valid @RequestBody ArticleDTO request,
                                          @PathVariable UUID id) {
        return ResponseEntity.ok(service.update(id, request));
    }

    // 3. Delete Article (MODERATOR)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
