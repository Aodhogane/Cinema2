package com.example.OnlineSinema.service;

import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.dto.reviewDTO.ReviewInputDTO;
import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.SinemaContract.VM.form.review.ReviewFormModel;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ReviewsService {
    void update(int id, ReviewOutputDTO reviewOutputDTO);
    void save(ReviewInputDTO reviewInputDTO);
    ReviewOutputDTO findById(int id);
    void deleteById(int id);
    List<ReviewOutputDTO> findByFilmId(int id);
    List<ReviewOutputDTO> findByUserId(int id);
    List<ReviewOutputDTO> findAll();
    Page<ReviewOutputDTO> findAll(int page, int size);
    void updateRatingFilm(int id);
    Set<FilmCardDTO> getReviewsByUserId(int id);
}
