package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Genres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreRepository {
    void save(Genres genres);
    Genres findByName(String name);
    List<Genres> findAll();
    Page<Genres> findAll(Pageable pageable);
    Genres findById(int id);
    void update(Genres genres);
    void deleteById(int id);
}
