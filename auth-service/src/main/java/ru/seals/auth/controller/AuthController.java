package ru.seals.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.seals.auth.dto.LoginDTO;
import ru.seals.auth.dto.TokenDTO;
import ru.seals.auth.service.AuthService;

@RestController
@RequestMapping(value = "api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO dto) {
        return authService.login(dto);
    }

    @PostMapping("/refresh-tokens")
    public ResponseEntity<TokenDTO> refresh(@CookieValue(value = "refreshToken", defaultValue = "")
                                            String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}
