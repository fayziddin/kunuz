package dasturlash.uz.controller;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.enums.Language;
import dasturlash.uz.mapper.SectionMapper;
import dasturlash.uz.service.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/section")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService service;

    //1. Create  (ADMIN)
    //    (order_number,name_uz, name_ru, name_en)
    @PostMapping("")
    public ResponseEntity<SectionDTO> create(@Valid @RequestBody SectionDTO request){
        return ResponseEntity.ok(service.create(request));
    }

    //2. Update by id (ADMIN)
    //    (order_number,name_uz, name_ru, name_en)
    @PutMapping("/{id}")
    public ResponseEntity<SectionDTO> update(@Valid @RequestBody SectionDTO request,
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
    public ResponseEntity<Page<SectionDTO>> getAllPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(value = "size", defaultValue = "50") Integer size){
        return ResponseEntity.ok(service.getAllPagination(page-1, size));
    }
    // 5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
    //    (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.)
    @GetMapping("/by-lang")
    public ResponseEntity<List<SectionMapper>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") Language lang,
                                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(value = "size", defaultValue = "50") Integer size){
        return ResponseEntity.ok(service.getByLang(lang, page-1, size));
    }
}
