package ru.seals.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.seals.auth.dto.ResetPasswordResponseDTO;
import ru.seals.auth.dto.UserDTO;
import ru.seals.auth.model.enums.UserActions;
import ru.seals.auth.service.KeycloakUserService;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {
    @Value("${keycloak.realm}")
    private String realmName;

    private final String keycloakPhoneName = "phoneNumber";
    private final Keycloak keycloak;
    private UsersResource getUsersResource() {
        RealmResource realm = keycloak.realm(realmName);
        return realm.users();
    }

    private UserRepresentation mapUserRep(UserDTO user, UserResource userRes) {
        UserRepresentation userRep = userRes.toRepresentation();
        userRep.setFirstName(user.getFirstName());
        userRep.setLastName(user.getLastName());

        if (userRep.getAttributes() == null)
            userRep.setAttributes(new HashMap<>());
        if (user.getPhone() != null && !user.getPhone().isBlank())
            userRep.getAttributes().put(keycloakPhoneName, Collections.singletonList(user.getPhone()));
        if (user.getEmail() != null && !userRep.getEmail().equals(user.getEmail())) {
            userRep.setEmail(user.getEmail());
            userRep.setEmailVerified(false);
        }

        return userRep;
    }

    private String getUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public UserRepresentation getById(String id) {
        return getUsersResource().get(id).toRepresentation();
    }

    @Override
    public UserRepresentation getAuthenticatedUser() {
        return getById(getUserId());
    }

    @Override
    public UserRepresentation updateById(String id, UserDTO user) {
        UserResource userRes = getUsersResource().get(id);
        UserRepresentation userRep = mapUserRep(user, userRes);
        userRes.update(userRep);
        log.info("User %s updated".formatted(id));
        return userRes.toRepresentation();
    }

    @Override
    public UserRepresentation updateAuthenticatedUser(UserDTO user) {
        return updateById(getUserId(), user);
    }

    @Override
    public ResetPasswordResponseDTO resetPassword() {
        UserResource userRes = getUsersResource().get(getUserId());
        String email = userRes.toRepresentation().getEmail();
        userRes.executeActionsEmail(Collections.singletonList(UserActions.UPDATE_PASSWORD.name()));
        log.info("Sent reset pass instructions to %s".formatted(email));
        return new ResetPasswordResponseDTO("Instructions was sent to %s".formatted(email));
    }

    @Override
    public void set2FA(boolean enabled) {
        UserResource userRes = getUsersResource().get(getUserId());
        UserRepresentation userRep = userRes.toRepresentation();
        if (enabled) {
            userRep.getRequiredActions().add(UserActions.CONFIGURE_TOTP.name());
            log.info("Enable 2FA to user %s".formatted(getUserId()));
        } else {
            userRep.getRequiredActions().remove(UserActions.CONFIGURE_TOTP.name());
            userRes.credentials().forEach(cred -> {
                if (cred.getType().equals("otp"))
                    userRes.removeCredential(cred.getId());
            });
            log.info("Disable 2FA and rm all devices to user %s".formatted(getUserId()));
        }
        userRes.update(userRep);
    }
}
