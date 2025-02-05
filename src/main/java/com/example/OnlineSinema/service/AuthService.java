package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.BaseUserDTO;
import com.example.OnlineSinema.DTO.UserRegistrationDTO;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    void register(UserRegistrationDTO regDTO);

    BaseUserDTO getUser(String email);
}
