//package com.example.OnlineSinema.service.impl;
//
//import com.example.OnlineSinema.domain.User;
//import com.example.OnlineSinema.exceptions.UserNotFound;
//import com.example.OnlineSinema.repository.UserRepository;
//import org.apache.catalina.UserDatabase;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//public class CustomUserDetailsService implements UserDetailsService {
//    private UserRepository userRepository;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            throw new UserNotFound("User not found with this email: " + email);
//        }
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getName(),
//                user.getEmail(),
//                user.getPassword(),
//                getAuthorities(user));
//    }
//
//    private Collection<>
//}
