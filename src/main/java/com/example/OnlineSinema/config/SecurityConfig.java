//package com.example.OnlineSinema.config;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http
//                .authorizeHttpRequests(authorization-> authorization
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/user/**").hasRole("USER")
//                        .requestMatchers("/main").permitAll()
//
//                        .anyRequest().permitAll()
//                )
//                .formLogin(form -> form
//                        .loginPage("")
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .permitAll()
//                );
//        return http.build();
//    }
//
////    @Bean
////    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
////        AuthenticationManagerBuilder authManagerBuilder =
////                http.getSharedObject(AuthenticationManagerBuilder.class);
////        authManagerBuilder
////                .userDetailsService(userDetailsService)
////                .passwordEncoder(passwordEncoder());
////
////        return authManagerBuilder.build();
////    }
//}