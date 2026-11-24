package dasturlash.uz.controller;

import dasturlash.uz.dto.auth.AuthResponseDTO;
import dasturlash.uz.dto.auth.AuthRequestDTO;
import dasturlash.uz.dto.auth.RegistrationConfirmDTO;
import dasturlash.uz.dto.auth.RegistrationDTO;
import dasturlash.uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO dto) {
//        return ResponseEntity.ok(authService.login(dto));
        return ResponseEntity.ok(authService.authentication(dto));
    }

    @PostMapping("/registration/verification")
    public ResponseEntity<String> verification(@RequestBody RegistrationConfirmDTO dto) {
        return ResponseEntity.ok(authService.regSmsVerification(dto.getUsername(), dto.getCode()));
    }

}
