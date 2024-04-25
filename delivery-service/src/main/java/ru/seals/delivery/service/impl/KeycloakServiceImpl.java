package ru.seals.delivery.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.seals.delivery.service.KeycloakService;

public class KeycloakServiceImpl implements KeycloakService {
    @Override
    public String getKeycloakUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
