package com.example.OnlineSinema.config;

import com.example.OnlineSinema.controller.MainPageController;
import com.example.OnlineSinema.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger LOG = LogManager.getLogger(MainPageController.class);

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with email " + username + " not found");
        }

        LOG.info("Loaded user: {}", user);

        if (user.getAccess() == null || user.getAccess().getRegistered() == null) {
            throw new UsernameNotFoundException("User " + username + " has no valid access role!");
        }

        String role = "ROLE_" + user.getAccess().getRegistered().toUpperCase();

        if (!role.equals("ROLE_ADMIN") && !role.equals("ROLE_USER")) {
            throw new UsernameNotFoundException("Invalid role for user: " + username);
        }

        return new CustomUser(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(role)),
                user.getId(),
                user.getUsername()
        );

    }

    public static class CustomUser implements UserDetails {
        private String username;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;
        private int id;
        private String customUsername;

        public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                          int id, String customUsername) {
            this.username = username;
            this.password = password;
            this.authorities = authorities;
            this.id = id;
            this.customUsername = customUsername;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        public int getId() {
            return id;
        }

        public String getCustomUsername() {
            return customUsername;
        }
    }
}
