package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.DTO.inputDTO.FilmInputDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

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
    void create(FilmInputDTO filmInputDTO);
    void delete(int filmId);
}
