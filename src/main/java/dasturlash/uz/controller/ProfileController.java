package dasturlash.uz.controller;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.dto.ProfileFilterDTO;
import dasturlash.uz.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService service;
    /*
    3. Update Profile Detail (ANY) (Profile updates own details)
    6. Update Photo (ANY) (Murojat qilgan odamni rasmini upda qilish)   (remove old image)
           photo_id
    * */

    // 1. Create profile (ADMIN) (can create MODERATOR,PUBLISHER))
    // (name,surname,email,phone,password,status,role)
    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileDTO request){
        return ResponseEntity.ok(service.create(request));
    }

    // 2. Update Profile (ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> update(@Valid @RequestBody ProfileDTO request,
                                          @PathVariable("id") Long id){
        return ResponseEntity.ok(service.update(id, request));
    }

    // 4. Profile List (ADMIN) (Pagination)
    @GetMapping("")
    public ResponseEntity<Page<ProfileDTO>> getAllPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(value = "size", defaultValue = "50") Integer size){
        return ResponseEntity.ok(service.getAllPagination(page-1, size));
    }

    // 5. Delete Profile By id (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    // 7. Filter (name,surname,phone,role,created_date_from,created_date_to)
    @PostMapping("/filter")
    public ResponseEntity<Page<ProfileDTO>> filter(@RequestBody ProfileFilterDTO filterDto,
                                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "size", defaultValue = "50") Integer size){
        return ResponseEntity.ok(service.filter(filterDto, page-1, size));
    }

}
