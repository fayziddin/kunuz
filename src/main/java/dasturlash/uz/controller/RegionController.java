package dasturlash.uz.controller;

import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.enums.LanguageEnum;
import dasturlash.uz.mapper.RegionMapper;
import dasturlash.uz.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService service;

    //1. Create  (ADMIN)
    //    (order_number,name_uz, name_ru, name_en)
    @PostMapping("")
    public ResponseEntity<RegionDTO> create(@Valid @RequestBody RegionDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }

    //2. Update by id (ADMIN)
    //    (key,name_uz, name_ru, name_en)
    @PutMapping("/{id}")
    public ResponseEntity<RegionDTO> update(@Valid @RequestBody RegionDTO dto,
                                          @PathVariable Long id){
        return ResponseEntity.ok(service.update(dto, id));
    }

    // 3. Delete by id (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok(service.delete(id));
    }

    // 4. Get List (ADMIN)
    //    (id,key,name_uz, name_ru, name_en,visible,created_date)
    @GetMapping("")
    public ResponseEntity<List<RegionDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    // 5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
    //    (id,key,name) (name ga tegishli name_.. dagi qiymat qo'yiladi.) (visible true)
    @GetMapping("/by-lang")
    public ResponseEntity<List<RegionMapper>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") LanguageEnum lang){
        return ResponseEntity.ok(service.getByLang(lang));
    }
}
