package dasturlash.uz.controller;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.CategoryShortInfo;
import dasturlash.uz.enums.Language;
import dasturlash.uz.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    // 1. Create  (ADMIN)
    //     (order_number,name_uz, name_ru, name_en)
    @PostMapping("")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO request){
        return ResponseEntity.ok(service.create(request));
    }

    // 2. Update by id (ADMIN)
    //     (order_number,name_uz, name_ru, name_en)
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@Valid @RequestBody CategoryDTO request,
                                          @PathVariable Long id){
        return ResponseEntity.ok(service.update(request, id));
    }

    // 3. Delete by id (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    // 4. Get List (ADMIN) - order by order_number
    //     (id,order_number,name_uz, name_ru, name_en,visible,created_date)
    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    // 5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
    //      (id,order_number,name) (name ga tegishli name_... dagi qiymat qo'yiladi.)
    @GetMapping("/by-lang")
    public ResponseEntity<List<CategoryShortInfo>> getByLang(@RequestParam("lang") Language lang){
        return ResponseEntity.ok(service.getByLang(lang));
    }
}
