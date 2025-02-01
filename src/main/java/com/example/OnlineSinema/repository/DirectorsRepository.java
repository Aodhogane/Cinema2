package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Directors;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DirectorsRepository {
    List<Directors> getAll(Class<Directors> entityClass);
    Page<Directors> findAllPage(Class<Directors> entityClass, int page, int size);
    Directors findById(Class<Directors> entityClass, int id);
    void create(Directors entity);
    void update(Directors entity);
    void delete(Directors entity);


    Directors findDirectorById(int directorId);
}
