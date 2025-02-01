package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.UserDTO;

public interface UserService {
    UserDTO findById(int clientId);
}
