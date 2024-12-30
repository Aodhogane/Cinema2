package com.example.OnlineSinema.config;

import com.example.OnlineSinema.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.stream.Collectors;

public class AppUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.example.OnlineSinema.domain.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found!"));

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new UsernameNotFoundException("Password is null or empty for user: " + username);
        }

        String role = "ROLE_" + user.getAccess().getRegistered().toUpperCase();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}