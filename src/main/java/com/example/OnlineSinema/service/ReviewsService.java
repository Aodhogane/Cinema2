package com.example.OnlineSinema.service;

import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.SinemaContract.VM.form.review.ReviewFormModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ReviewsService {
    void update(int id, ReviewOutputDTO reviewOutputDTO);
    void save(ReviewFormModel reviewFormModel);
    ReviewOutputDTO findById(int id);
    void deleteById(int id);
    List<ReviewOutputDTO> findByFilmId(int id);
    List<ReviewOutputDTO> findByUserId(int id);
    List<ReviewOutputDTO> getLastReviewsByUserId(int id, int count);
    Page<ReviewOutputDTO> getLastReviewsByFilmId(int id, int reviewPage, int reviewSize);
    Page<ReviewOutputDTO> getReviewsByUserId(int id, int reviewPage, int reviewSize);
    List<ReviewOutputDTO> findByRating(int filmId, float rating);
    ReviewOutputDTO findByUserIdFilmId(int userId, int filmId);
    List<ReviewOutputDTO> findAll();
    Page<ReviewOutputDTO> findAll(int page, int size);
    void updateRatingFilm(int id);
    Set<FilmCardDTO> getReviewsByUserId(int id);
}
