package ru.seals.auth.service;

import org.springframework.http.ResponseEntity;
import ru.seals.auth.dto.LoginDTO;
import ru.seals.auth.dto.TokenDTO;

public interface AuthService {
    ResponseEntity<TokenDTO> login(LoginDTO loginDTO);
    ResponseEntity<TokenDTO> refreshToken(String refreshToken);
}
