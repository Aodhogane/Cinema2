package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Reviews;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewRepository {
    List<Reviews> getAll(Class<Reviews> entityClass);
    Page<Reviews> findAllPage(Class<Reviews> entityClass, int page, int size);
    Reviews findById(Class<Reviews> entityClass, int id);
    void create(Reviews entity);
    void update(Reviews entity);
    void delete(Reviews entity);

    List<Reviews> findReviewByFilmId(int filmId);
    List<Reviews> findReviewByClientId(int clientId);
}
