package ru.seals.auth.service;

import org.keycloak.representations.idm.UserRepresentation;
import ru.seals.auth.dto.ResetPasswordResponseDTO;
import ru.seals.auth.dto.UserDTO;

public interface KeycloakUserService {
    UserRepresentation getById(String id);
    UserRepresentation getAuthenticatedUser();
    UserRepresentation updateById(String id, UserDTO user);
    UserRepresentation updateAuthenticatedUser(UserDTO user);
    ResetPasswordResponseDTO resetPassword();
    void set2FA(boolean enabled);
}
