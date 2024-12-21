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
    UserOutputDTO findById(int id);
    void update(UserOutputDTO userOutputDTO);
    UserOutputDTO findByName(String name);
    boolean authenticateUser(String email, String password);
    Page<UserOutputDTO> findAll(int page, int size);
    void delete(int id);
    boolean register(String username, String password, String email);
    String findUserNameById(int id);
    UserInfoDTO findByUsername(String name);

}
