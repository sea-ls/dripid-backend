package ru.seals.auth.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.seals.auth.dto.ResetPasswordResponseDTO;
import ru.seals.auth.dto.UserDTO;
import ru.seals.auth.service.KeycloakUserService;

@RestController
@RequestMapping("api/auth-service/user")
@RequiredArgsConstructor
public class UserController {
    private final KeycloakUserService userService;

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDTO getById(@PathVariable String id) {
        return userService.mapUser(userService.getById(id));
    }

    @GetMapping("authenticated")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDTO getAuthenticatedUser() {
        return userService.mapUser(userService.getAuthenticatedUser());
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserRepresentation updateById(@PathVariable String id, @RequestBody UserDTO user) {
        return userService.updateById(id, user);
    }

    @PutMapping("authenticated")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserRepresentation updateAuthenticatedUser(@RequestBody UserDTO user) {
        return userService.updateAuthenticatedUser(user);
    }

    @PostMapping("reset-password")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResetPasswordResponseDTO resetPassword() {
        return userService.resetPassword();
    }

    @PostMapping("set-2fa")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void set2FA(@RequestParam boolean enabled) {
        userService.set2FA(enabled);
    }
}
