package dasturlash.uz.controller;

import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.dto.ArticleStatusDTO;
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

    // 1. CREATE (Moderator) status(NotPublished)
    // Request: (title,description,content,imageId, regionId, categoryList[], sectionList[] )
    // Response: ArticleInfoDTO
    @PostMapping("")
    public ResponseEntity<ArticleDTO> create(@Valid @RequestBody ArticleDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    // 2. Update (Moderator (status to not publish)) (remove old image)
    // Request: (title,description,content,imageId, regionId, categoryList[], sectionList[])
    // Response: ArticleInfoDTO
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> update(@Valid @RequestBody ArticleDTO request,
                                          @PathVariable UUID id) {
        return ResponseEntity.ok(service.update(id, request));
    }

    // 3. Delete Article (MODERATOR)
    // Request: id
    // Response: "Message"
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }

    // 4. Change status by id (PUBLISHER) (publish,not_publish)
    // Request: id, (status)
    // Response: "Message"
    @PatchMapping("/{id}/status")
    public ResponseEntity<Boolean> changeStatus(@PathVariable UUID id,
                                                @Valid @RequestBody ArticleStatusDTO request){
        return ResponseEntity.ok(service.changeStatus(id, request));
    }

}
