package com.example.OnlineSinema.service;

import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmSalesDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface FilmService {
    void save(FilmOutputDTO filmOutputDTO);
    List<FilmCardDTO> findAll();
    Page<FilmCardDTO> findAll(int page, int size);
    List<FilmCardDTO> findByNameContaining(String title);
    List<FilmCardDTO> findByGenres(List<String> genres);
    Page<FilmCardDTO> findByGenre(String genre, int page, int size);
    FilmOutputDTO findById(int id);
    void update(int id, String title, List<String> genres);
    void deleteById(int id);
    List<FilmSalesDTO> getTop5FilmsBySales();
    void updateRatingFilm(int id_film);
    FilmOutputDTO findByTitle(String title);
    Page<FilmCardDTO> findByNameContainingAndByGenres(String filmPart, List<String> genres, int page, int size);
    List<String> getAllGenres();
    Film findFilmWithDetails(int filmId);
    Page<FilmCardDTO> searchByTitle(String title, int page, int size);
}
