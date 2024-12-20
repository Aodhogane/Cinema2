package com.example.OnlineSinema.service;

import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmSalesDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilmService {
    void save(FilmOutputDTO filmOutputDTO);
    List<FilmCardDTO> findAll();
    Page<FilmCardDTO> findAll(int page, int size);
    List<FilmCardDTO> findByGenres(List<String> genres);
    Page<FilmCardDTO> findByGenre(String genre, int page, int size);
    FilmOutputDTO findById(int id);
    void update(int id, String title, List<String> genres);
    void deleteById(int id);
    List<FilmSalesDTO> getTop5FilmsBySales();
    List<FilmOutputDTO> findTopFilmsByReviewCount(boolean isTop);
    void updateRatingFilm(int id_film);
    FilmOutputDTO findByTitle(String title);
    List<String> getAllGenres();
    String findFilmNameById(Long filmId);
}
