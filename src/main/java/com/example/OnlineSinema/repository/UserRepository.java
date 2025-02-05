package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserRepository {
    List<User> getAll(Class<User> entityClass);
    Page<User> findAllPage(Class<User> entityClass, int page, int size);
    User findById(Class<User> entityClass, int id);
    void create(User entity);
    void update(User entity);
    void delete(User entity);

    User findUserByEmail(String emailUser);
}
