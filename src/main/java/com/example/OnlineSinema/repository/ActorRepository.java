package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Actors;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActorRepository {
    List<Actors> getAll(Class<Actors> entityClass);
    Page<Actors> findAllPage(Class<Actors> entityClass, int page, int size);
    Actors findById(Class<Actors> entityClass, int id);
    void create(Actors entity);
    void update(Actors entity);
    void delete(Actors entity);

    List<Actors> findActorsByFilmId(int filmId);

    Actors findActorsByUserId(int userId);
}
