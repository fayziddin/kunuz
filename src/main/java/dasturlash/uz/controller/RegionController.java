package dasturlash.uz.controller;

import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.dto.RegionShortInfo;
import dasturlash.uz.enums.Language;
import dasturlash.uz.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
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
    public ResponseEntity<Boolean> update(@Valid @RequestBody RegionDTO dto,
                                          @PathVariable Long id){
        return ResponseEntity.ok(service.update(dto, id));
    }

    // 3. Delete by id (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<List<RegionShortInfo>> getByLang(@RequestParam("lang") Language lang){
        return ResponseEntity.ok(service.getByLang(lang));
    }
}
