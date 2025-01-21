package com.example.OnlineSinema.config;

import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.OnlineSinema.domain.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found!"));

        if (user.getAccess() == null || user.getAccess().getRegistered() == null) {
            throw new UsernameNotFoundException("User " + username + " has no valid access role!");
        }

        String role = "ROLE_" + user.getAccess().getRegistered().toUpperCase();
        System.out.println("Loading user: " + username + " with role: " + role);

        if (!role.equals("ROLE_ADMIN") && !role.equals("ROLE_USER")) {
            throw new UsernameNotFoundException("Invalid role for user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}
