package com.example.OnlineSinema.repository.impl;

import com.example.OnlineSinema.domain.Reviews;
import com.example.OnlineSinema.repository.ReviewsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewsRepositoryImpl implements ReviewsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Reviews review) {
        if (entityManager.contains(review)) {
            entityManager.merge(review);
        } else {
            entityManager.persist(review);
        }
    }

    @Override
    public Reviews findById(int id) {
        try {
            return entityManager.createQuery("SELECT r FROM Reviews r WHERE r.id = :id", Reviews.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Page<Reviews> getLatestReviewsByFilmId(int filmId, Pageable pageable) {
        Long reviewCount = (Long) entityManager.createQuery("SELECT COUNT(r) FROM Reviews r JOIN r.film f WHERE f.id = :filmId")
                .setParameter("filmId", filmId)
                .getSingleResult();

        List<Reviews> reviews = entityManager.createQuery("SELECT r FROM Reviews r JOIN r.film f WHERE f.id = :filmId ORDER BY r.dateTime DESC", Reviews.class)
                .setParameter("filmId", filmId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(reviews, pageable, reviewCount);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Reviews review = entityManager.find(Reviews.class, id);
        if (review != null) {
            entityManager.remove(review);
        }
    }

    @Override
    public List<Reviews> findByFilmId(int filmId) {
        return entityManager.createQuery(
                        "SELECT r FROM Reviews r WHERE r.film.id = :filmId", Reviews.class)
                .setParameter("filmId", filmId)
                .getResultList();
    }

    @Override
    public List<Reviews> findByUserId(int userId) {
        return entityManager.createQuery(
                        "SELECT r FROM Reviews r WHERE r.user.id = :userId", Reviews.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Reviews> getLastReviewsByUserId(int userId, int count) {
        return entityManager.createQuery("SELECT r FROM Reviews r JOIN r.user u WHERE u.id = :userId ORDER BY r.dateTime DESC", Reviews.class)
                .setParameter("userId", userId)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    public List<Reviews> findByFilmIdAndRating(int filmId, float rating) {
        return entityManager.createQuery(
                        "SELECT r FROM Reviews r WHERE r.film.id = :filmId AND r.estimation = :rating", Reviews.class)
                .setParameter("filmId", filmId)
                .setParameter("rating", rating)
                .getResultList();
    }

    @Override
    public List<Reviews> findByFilmIdAndUserId(int filmId, int userId) {
        return entityManager.createQuery(
                        "SELECT r FROM Reviews r WHERE r.film.id = :filmId AND r.user.id = :userId", Reviews.class)
                .setParameter("filmId", filmId)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    @Transactional
    public void update(Reviews review) {
        if (review != null) {
            entityManager.merge(review);
        }
    }

    @Override
    public List<Reviews> findAll() {
        return entityManager.createQuery("SELECT r FROM Reviews f", Reviews.class)
                .getResultList();
    }
}