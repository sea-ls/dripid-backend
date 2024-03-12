package ru.seals.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.seals.auth.model.User;
import ru.seals.auth.repo.UserRepo;
import ru.seals.auth.security.DripIdUserDetails;
import ru.seals.auth.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public User getByEmail(String email) throws UsernameNotFoundException {
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", email)));
    }

    @Override
    public User getByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    @Override
    public User save(User user) {
        return userRepo.saveAndFlush(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByEmail(username);
        return new DripIdUserDetails(user.getUsername(), user.getPassword(), user.getRole());
    }
}
