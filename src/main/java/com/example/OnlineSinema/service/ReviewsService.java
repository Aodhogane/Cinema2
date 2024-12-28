package com.example.OnlineSinema.service;

import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.SinemaContract.VM.form.review.ReviewFormModel;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewsService {
//    void save(ReviewOutputDTO reviewOutputDTO);
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

    @Transactional
    void updateRatingFilm(int id);
}
