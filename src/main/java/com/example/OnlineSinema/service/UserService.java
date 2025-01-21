package com.example.OnlineSinema.service;

import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.dto.userDTO.UserInfoDTO;
import com.example.OnlineSinema.dto.userDTO.UserOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    void save(UserOutputDTO userOutputDTO);
    List<UserOutputDTO> findAll();
    UserInfoDTO findById(int id);
    void update(UserOutputDTO userOutputDTO);
    UserOutputDTO findByName(String email);
    Page<UserInfoDTO> findAll(int page, int size);
    void delete(int id);
    void register(String username, String email, String password, int accessId);
    UserInfoDTO findByUsername(String username);
}
