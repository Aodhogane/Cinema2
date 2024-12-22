package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewsRepository {
    void save(Reviews review);
    List<Reviews> findByFilmId(int filmId);
    List<Reviews> findByUserId(int userId);
    List<Reviews> getLastReviewsByUserId(int id, int count);
    List<Reviews> findByFilmIdAndRating(int filmId, float rating);
    List<Reviews> findByFilmIdAndUserId(int filmId, int userId);
    void deleteById(int id);
    Reviews findById(int id);
    Page<Reviews> getLatestReviewsByFilmId(int id, Pageable pageable);
    void update(Reviews reviews);
    List<Reviews> findAll();
    Page<Reviews> findAll(Pageable pageable);
}

