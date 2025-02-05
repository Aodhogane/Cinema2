package com.example.OnlineSinema.service.Impl;

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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email);

        UserDetails us = new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getUserRoles().name())));

        return us;
    }
}