package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Film;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilmRepository {
    List<Film> getAll(Class<Film> entityClass);
    Page<Film> findAllPage(Class<Film> entityClass, int page, int size);
    Film findById(Class<Film> entityClass, int id);
    void create(Film entity);
    void update(Film entity);
    void delete(Film entity);

    Page<Film> findFilmByTitle(String title, int page, int size);
    Page<Film> findFilmByGenres(String genre, int page, int size);
    List<Film> findFilmByActorsId(int actorsId);
    List<Film> findFilmsByDirectorsId(int directorsId);
}
