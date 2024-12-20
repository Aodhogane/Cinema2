package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.domain.Directors;
import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.domain.Genres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilmRepository {
    void save(Film film);
    void update(Film film);
    void deleteById(int id);
    Film findByTitle(String title);
    Film findFilmWithDetails(int filmId);
    List<Film> findByGenres(List<Genres> genresList);
    Page<Film> findByGenres(List<Genres> genresList, Pageable pageable);
    Film findById(int filmId);
    List<Film> findAll();
    Page<Film> findAll(Pageable pageable);
    List<Film> findTopFilmsByReviewCount(boolean isTop);
    List<Film> findTop5FilmsBySales();
    Page<Film> findByGenre(String genre, Pageable pageable);
    List<Film> findFilmsByActorId(int actorId);
    List<Film> findFilmsByDirectorId(int directorId);
}
