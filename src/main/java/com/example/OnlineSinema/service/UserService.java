package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.UserDTO;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserDTO findById(int clientId);
    void update(UserDTO userDTO, int userId);
    void create(UserDTO userDTO);
    void delete(int userId);
}
