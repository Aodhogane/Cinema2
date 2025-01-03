package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface UserRepository {
    void save(User user);
    void update(User user);
    void deleteById(int id);
    User findByEmail(String email);
    User findByName(String name);
    User findById(int id);
    List<User> findAll();
    Page<User> findAll(Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    List<User> findByAccessId(int accessId);
}