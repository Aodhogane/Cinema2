package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Directors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DirectorRepository {
    void save(Directors director);
    Directors findById(int id);
    Directors findByName(String name, String surname, String middleName);
    List<Directors> findAll();
    Page<Directors> findAll(Pageable pageable);
    List<Directors> findByFilmId(int id);
    void deleteById(int id);
    void update(Directors directors);
}
