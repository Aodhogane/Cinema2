package com.example.OnlineSinema.config;

import com.example.OnlineSinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import static com.example.OnlineSinema.enums.UserRole.ADMIN;

@Configuration
public class AppSecurityConfiguration {

    private final UserRepository userRepository;

    @Autowired
    public AppSecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {
        http
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/main", "/search", "/film/{id}", "/user/{id}", "/auth/**").permitAll()
                                .requestMatchers("/review/**", "/reaction/**", "/comment/**").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/user/login")
                                .successHandler(successHandler())
                                .defaultSuccessUrl("/main", true)
                                .failureUrl("/auth/login?error=error")
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/user/logout")
                                .logoutSuccessUrl("/user/login")
                                .invalidateHttpSession(true)
                )
                .securityContext(securityContext ->
                        securityContext.securityContextRepository(securityContextRepository)
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .rememberMe(rememberMe ->
                        rememberMe
                                .rememberMeParameter("remember-me")
                                .tokenValiditySeconds(5 * 60)
                                .key("uniqueAndSecretKey")
                );

        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService(userRepository);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            String username = authentication.getName();
            System.out.println("Успешный вход пользователя: " + username);
            response.sendRedirect("/main?success");
        };
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            System.out.println("Ошибка входа: " + exception.getMessage());
            response.sendRedirect("/login?error");
        };
    }
}