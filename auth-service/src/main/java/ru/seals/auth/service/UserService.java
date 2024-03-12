package ru.seals.auth.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.seals.auth.model.User;

public interface UserService {
    User getByEmail(String email) throws UsernameNotFoundException;
    User getByUsername(String username) throws UsernameNotFoundException;
    User save(User user);
}
