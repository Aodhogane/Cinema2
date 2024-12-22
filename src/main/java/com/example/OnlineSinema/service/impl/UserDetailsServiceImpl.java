package com.example.OnlineSinema.service.impl;

import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByEmail(username);

        if(username == null){
            throw new UsernameNotFoundException("Client with email:" + username + " not found");
        }
        return new CustomUser(user.getName(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_"+user.getAccess().getRegistered())),
                user.getId(), user.getName());
    }

    public static class CustomUser extends User{
        private final int id;
        private final String name;

        public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, int id, String name) {
            super(username, password, authorities);
            this.id = id;
            this.name = name;
        }

        public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, int id, String name) {
            super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
