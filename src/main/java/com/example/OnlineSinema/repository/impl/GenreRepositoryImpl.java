package com.example.OnlineSinema.repository.impl;

import com.example.OnlineSinema.domain.Genres;
import com.example.OnlineSinema.repository.GenreRepository;
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
public class GenreRepositoryImpl implements GenreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Genres genres) {
        if (entityManager.contains(genres)) {
            entityManager.merge(genres);
        } else {
            entityManager.persist(genres);
        }
    }

    @Override
    public Genres findByName(String name) {
        try {
            return entityManager.createQuery("SELECT g FROM Genres g WHERE LOWER(g.genres) = LOWER(:name)", Genres.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Genres findById(int id) {
        try {
            return entityManager.createQuery("SELECT g FROM Genres g WHERE g.id = :id", Genres.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Genres> findAll() {
        return entityManager.createQuery("SELECT g FROM Genres g", Genres.class).getResultList();
    }

    @Override
    public Page<Genres> findAll(Pageable pageable) {
        long genreCount = entityManager.createQuery("SELECT COUNT(g) FROM Genres g", Long.class)
                .getSingleResult();

        List<Genres> genres = entityManager.createQuery("SELECT g FROM Genres g", Genres.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(genres, pageable, genreCount);
    }

    @Override
    @Transactional
    public void update(Genres genres) {
        entityManager.merge(genres);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Genres genre = findById(id);
        if (genre != null) {
            entityManager.remove(genre);
        }
    }
}