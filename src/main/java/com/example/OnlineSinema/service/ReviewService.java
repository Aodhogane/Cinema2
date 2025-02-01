package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> findReviewByFilmId(int filmId);
    List<ReviewDTO> findReviewByClientId(int clientId);
    ReviewDTO findReviewById(int reviewId);
    void addReview(ReviewDTO reviewDTO);

    Page<ReviewDTO> findAllPage(int page, int size);
}
