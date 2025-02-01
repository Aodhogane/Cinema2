package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.FilmDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilmService {
    Page<FilmDTO> findAllPage(int page, int size);

    Page<FilmDTO> findFilmByTitle(String title, int page, int size);
    Page<FilmDTO> filmFilmByGenre(String genre, int page, int size);

    Page<FilmDTO> chuzSort(String title, String genre, int page, int size);
    FilmDTO findFilmById(int filmId);
    void updateRating(int filmId);
    void update(FilmDTO filmDTO, int filmId);
    List<FilmDTO> findFilmsByActorsId(int actorsId);
    List<FilmDTO> findFilmsByDirectorsId(int directorsId);
}
