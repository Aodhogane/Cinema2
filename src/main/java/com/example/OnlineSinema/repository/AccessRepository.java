package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRepository {
    Access findByName(String registered);
    void save(Access role);
    List<Access> findAll();
    Access findById(int id);
}