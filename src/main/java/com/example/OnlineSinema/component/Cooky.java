package com.example.OnlineSinema.component;

import com.example.OnlineSinema.config.AppSecurityConfiguration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Cooky implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();

        Cookie userCooky = new Cookie("logetUserId", username);
        userCooky.setMaxAge(5 * 60); //5 минут
        userCooky.setPath("/");

        response.addCookie(userCooky);

        response.sendRedirect("/main");
    }
}
