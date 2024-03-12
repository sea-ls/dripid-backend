package ru.seals.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.seals.auth.dto.LoginDTO;
import ru.seals.auth.dto.TokenDTO;
import ru.seals.auth.model.User;
import ru.seals.auth.model.enums.Role;
import ru.seals.auth.security.DripIdUserDetails;
import ru.seals.auth.service.AuthService;
import ru.seals.auth.service.JwtService;
import ru.seals.auth.service.UserService;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Value("${security.access-token.expInMin}")
    private int accessTokenExp;
    @Value("${security.refresh-token.expInMin}")
    private int refreshTokenExp;
    @Override
    public ResponseEntity<TokenDTO> login(LoginDTO loginDTO) {
        User user;
        try {
            user = userService.getByEmail(loginDTO.getEmail());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(),
                    loginDTO.getPassword()
            ));
        } catch (UsernameNotFoundException e) {
            user = new User();
            user.setEmail(loginDTO.getEmail());
            user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
            user.setRole(Role.ROLE_SUPERADMIN);
            userService.save(user);
        }

        DripIdUserDetails userDetails = new DripIdUserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getRole());

        String accessToken = jwtService.generateToken(userDetails, accessTokenExp);
        String newRefreshToken = jwtService.generateToken(userDetails, refreshTokenExp);
        ResponseCookie cookie = createRefreshTokenCookie(newRefreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new TokenDTO(accessToken));
    }
    @Override
    public ResponseEntity<TokenDTO> refreshToken(String refreshToken) {
        return null;
    }
    private ResponseCookie createRefreshTokenCookie(String token) {
        return ResponseCookie.from("refreshToken")
                .value(token)
                .path("/api/v1/auth/")
                .maxAge(Duration.ofMinutes(refreshTokenExp))
                .httpOnly(true)
                .secure(true)
                .build();
    }
}
