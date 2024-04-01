package ru.seals.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.seals.auth.dto.ResetPasswordResponseDTO;
import ru.seals.auth.dto.UserDTO;
import ru.seals.auth.service.KeycloakUserService;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {
    @Value("${keycloak.realm}")
    private String realmName;
    private final Keycloak keycloak;
    private UsersResource getUsersResource() {
        RealmResource realm = keycloak.realm(realmName);
        return realm.users();
    }

    private List<UserDTO> mapUsers(List<UserRepresentation> userRepresentations) {
        List<UserDTO> users = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(userRepresentations)) {
            userRepresentations.forEach(userRep -> {
                users.add(mapUser(userRep));
            });
        }
        return users;
    }

    @Override
    public UserDTO mapUser(UserRepresentation userRep) {
        UserDTO user = new UserDTO();
        user.setEmail(userRep.getEmail());
        user.setFirstName(userRep.getFirstName());
        user.setLastName(userRep.getLastName());
        user.setPhone(userRep.getAttributes().get("phone").get(0));
        return user;
    }

    private UserRepresentation mapUserRep(UserDTO user, UserResource userRes) {
        UserRepresentation userRep = userRes.toRepresentation();
        userRep.setFirstName(user.getFirstName());
        userRep.setLastName(user.getLastName());

        if (userRep.getAttributes() == null)
            userRep.setAttributes(new HashMap<>());
        if (user.getPhone() != null)
            userRep.getAttributes().put("phone", Collections.singletonList(user.getPhone()));
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
        return userRes.toRepresentation();
    }

    @Override
    public UserRepresentation updateAuthenticatedUser(UserDTO user) {
        return updateById(getUserId(), user);
    }

    @Override
    public ResetPasswordResponseDTO resetPassword() {
        UserResource userRes = getUsersResource().get(getUserId());
        userRes.executeActionsEmail(Collections.singletonList("UPDATE_PASSWORD"));
        return new ResetPasswordResponseDTO("Instructions was sent to email");
    }

    @Override
    public void set2FA(boolean enabled) {
        UserResource userRes = getUsersResource().get(getUserId());
        UserRepresentation userRep = userRes.toRepresentation();
        if (enabled) {
            userRep.getRequiredActions().add("CONFIGURE_TOTP");
        } else {
            userRep.getRequiredActions().remove("CONFIGURE_TOTP");
        }
        userRes.update(userRep);
    }
}
