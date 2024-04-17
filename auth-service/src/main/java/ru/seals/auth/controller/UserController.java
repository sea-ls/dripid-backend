package ru.seals.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.seals.auth.dto.ResetPasswordResponseDTO;
import ru.seals.auth.dto.UserDTO;
import ru.seals.auth.service.KeycloakUserService;
import ru.seals.auth.util.Convertor;

@RestController
@RequestMapping("api/auth-service/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final KeycloakUserService userService;
    private final Convertor convertor;

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Получение юзера по ID")
    public UserDTO getById(@PathVariable String id) {
        return convertor.mapUser(userService.getById(id));
    }

    @GetMapping("authenticated")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Получение аутентифицированного пользователя")
    public UserDTO getAuthenticatedUser() {
        return convertor.mapUser(userService.getAuthenticatedUser());
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Обновление юзера по ID и его DTO")
    public UserRepresentation updateById(@PathVariable String id, @RequestBody UserDTO user) {
        return userService.updateById(id, user);
    }

    @PutMapping("authenticated")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Обновление аутентифицированного пользователя по его DTO")
    public UserRepresentation updateAuthenticatedUser(@RequestBody UserDTO user) {
        return userService.updateAuthenticatedUser(user);
    }

    @PostMapping("reset-password")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Сброс пароля")
    public ResetPasswordResponseDTO resetPassword() {
        return userService.resetPassword();
    }

    @PostMapping("set-2fa")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Подключение двухфакторной аутентификации")
    public void set2FA(@RequestParam boolean enabled) {
        userService.set2FA(enabled);
    }
}
