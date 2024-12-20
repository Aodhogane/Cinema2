package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Actors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActorRepository {
    void save(Actors actor);
    void update(Actors actor);
    Actors findById(int id);
    Actors findByName(String name, String surname, String middleName);
    List<Actors> findAll();
    List<Actors> findByFilmId(int id);
    void deleteById(int id);
    Page<Actors> findAll(Pageable pageable);
}