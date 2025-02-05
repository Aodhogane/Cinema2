package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.domain.FilmActor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilmActorRepository {
    List<FilmActor> getAll(Class<FilmActor> entityClass);
    Page<FilmActor> findAllPage(Class<FilmActor> entityClass, int page, int size);
    FilmActor findById(Class<FilmActor> entityClass, int id);
    void create(FilmActor entity);
    void update(FilmActor entity);
    void delete(FilmActor entity);

    FilmActor findFilmActorByFilmIdAndActorId(int filmId, int actorId);
}
