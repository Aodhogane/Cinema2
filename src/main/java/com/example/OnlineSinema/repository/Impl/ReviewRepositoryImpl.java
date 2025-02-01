package com.example.OnlineSinema.repository.Impl;

import com.example.OnlineSinema.domain.Reviews;
import com.example.OnlineSinema.repository.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl extends BaseRepository<Reviews> implements ReviewRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Reviews> findReviewByFilmId(int filmId){
        return entityManager.createQuery("select r from Reviews r where r.film.id =: filmId", Reviews.class)
                .setParameter("filmId", filmId)
                .getResultList();
    }

    @Override
    public List<Reviews> findReviewByClientId(int clientId){
        return entityManager.createQuery("select r from Reviews r where r.client.id =: clientId", Reviews.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }
}
