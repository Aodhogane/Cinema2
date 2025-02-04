package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.ReviewDTO;
import com.example.OnlineSinema.DTO.inputDTO.ReviewInputDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> findReviewByFilmId(int filmId);
    List<ReviewDTO> findReviewByClientId(int clientId);
    ReviewDTO findReviewById(int reviewId);
    Page<ReviewDTO> findAllPage(int page, int size);
    void update(ReviewDTO reviewDTO, int reviewId);
    void create(ReviewInputDTO reviewInputDTO);
    void delete(int reviewId);
}
