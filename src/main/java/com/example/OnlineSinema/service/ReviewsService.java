package com.example.OnlineSinema.service;

import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewsService {
    void save(ReviewOutputDTO reviewOutputDTO);
    void update(int id, ReviewOutputDTO reviewOutputDTO);
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
}
