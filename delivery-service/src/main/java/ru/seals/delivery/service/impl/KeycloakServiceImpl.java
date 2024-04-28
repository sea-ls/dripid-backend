package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.seals.delivery.service.KeycloakService;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    @Override
    public String getKeycloakUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
