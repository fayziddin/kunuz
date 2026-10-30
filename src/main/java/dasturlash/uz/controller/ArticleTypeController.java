package dasturlash.uz.controller;

import dasturlash.uz.dto.ArticleTypeDTO;
import dasturlash.uz.enums.Language;
import dasturlash.uz.mapper.ArticleTypeMapper;
import dasturlash.uz.service.ArticleTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article-type")
@RequiredArgsConstructor
public class ArticleTypeController {
    private final ArticleTypeService service;

    //1. Create  (ADMIN)
    //    (order_number,name_uz, name_ru, name_en)
    @PostMapping("")
    public ResponseEntity<ArticleTypeDTO> create(@Valid @RequestBody ArticleTypeDTO request){
        return ResponseEntity.ok(service.create(request));
    }

    //2. Update by id (ADMIN)
    //    (order_number,name_uz, name_ru, name_en)
    @PutMapping("/{id}")
    public ResponseEntity<ArticleTypeDTO> update(@Valid @RequestBody ArticleTypeDTO request,
                                          @PathVariable("id") Long id){
        return ResponseEntity.ok(service.update(id, request));
    }

    //3. Delete by id (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    // 4. Get List (ADMIN) (Pagination)
    //        (id,key,name_uz, name_ru, name_en,visible,created_date)
    @GetMapping("")
    public ResponseEntity<Page<ArticleTypeDTO>> getAllPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                 @RequestParam(value = "size", defaultValue = "50") Integer size){
        return ResponseEntity.ok(service.getAllPagination(page-1, size));
    }
    // 5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
    //    (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.)
    @GetMapping("/by-lang")
    public ResponseEntity<List<ArticleTypeMapper>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") Language lang,
                                                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(value = "size", defaultValue = "50") Integer size){
        return ResponseEntity.ok(service.getByLang(lang, page-1, size));
    }
}
