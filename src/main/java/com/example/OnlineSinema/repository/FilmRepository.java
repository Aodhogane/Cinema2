package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.domain.Directors;
import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.domain.Genres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilmRepository {
    List<Film> findAll();
    Page<Film> findAll(Pageable pageable);

    Film findByTitle(String title);
    List<Film> findByTitleContaining(String titlePart);
    Page<Film> findByTitleContaining(String titlePart, Pageable pageable);

    Film findFilmWithDetails(int filmId);

    List<Film> findFilmsByActorId(int actorId);
    List<Film> findFilmsByDirectorId(int directorId);

    List<Film> findByGenres(List<Genres> genresList);
    Page<Film> findByGenres(List<Genres> genresList, Pageable pageable);

    List<Film> findTop5FilmsBySales();

    List<Film> findByReviewCount();
    Page<Film> findByReviewCount(Pageable pageable);

    Page<Film> findByTitleContainingAndGenres(String filmPart, List<Genres> genresList, Pageable  pageable);

    Film findById(int filmId);
    void save(Film film);
    void deleteById(int id);
    void update(Film film);
}