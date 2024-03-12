package ru.seals.auth.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.seals.auth.model.enums.Role;

import java.util.Collections;

public class DripIdUserDetails extends User {
    public DripIdUserDetails(String username, String password, Role role) {
        super(username, password, Collections.singletonList(new SimpleGrantedAuthority(role.name())));
    }
}
