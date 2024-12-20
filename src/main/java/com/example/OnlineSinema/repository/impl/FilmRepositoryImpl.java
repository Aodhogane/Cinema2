package com.example.OnlineSinema.repository.impl;

import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.domain.Genres;
import com.example.OnlineSinema.repository.FilmRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FilmRepositoryImpl implements FilmRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Film film) {
        if (entityManager.contains(film)) {
            entityManager.merge(film);
        } else {
            entityManager.persist(film);
        }
    }

    @Override
    public Film findByTitle(String title) {
        try {
            return entityManager.createQuery(
                            "SELECT f FROM Film f WHERE f.title = :title", Film.class)
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Film findFilmWithDetails(int filmId) {
        return entityManager.createQuery(
                        "SELECT f FROM Film f " +
                                "LEFT JOIN FETCH f.genresList " +
                                "LEFT JOIN FETCH f.directorsList " +
                                "LEFT JOIN FETCH f.actorsList " +
                                "LEFT JOIN FETCH f.reviewsList " +
                                "LEFT JOIN FETCH f.ticketsList " +
                                "WHERE f.id = :filmId", Film.class)
                .setParameter("filmId", filmId)
                .getSingleResult();
    }

    @Override
    public List<Film> findByGenres(List<Genres> genresList) {
        return entityManager.createQuery(
                        "SELECT DISTINCT f FROM Film f JOIN f.genresList g WHERE g IN :genresList GROUP BY f HAVING COUNT(DISTINCT g) = :genreCount", Film.class)
                .setParameter("genresList", genresList)
                .setParameter("genreCount", genresList.size())
                .getResultList();
    }

    @Override
    public Page<Film> findByGenres(List<Genres> genresList, Pageable pageable) {
        Long filmCount;
        try {
            filmCount = entityManager.createQuery(
                            "SELECT COUNT(DISTINCT f) FROM Film f JOIN f.genresList g WHERE g IN :genresList GROUP BY f HAVING COUNT(DISTINCT g) = :genreCount", Long.class)
                    .setParameter("genresList", genresList)
                    .setParameter("genreCount", genresList.size())
                    .getSingleResult();
        } catch (NoResultException e) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        var filmList = entityManager.createQuery(
                        "SELECT DISTINCT f FROM Film f JOIN f.genresList g WHERE g IN :genresList GROUP BY f HAVING COUNT(DISTINCT g) = :genreCount", Film.class)
                .setParameter("genresList", genresList)
                .setParameter("genreCount", genresList.size())
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(filmList, pageable, filmCount);
    }

    @Override
    public Film findById(int id) {
        try {
            return entityManager.createQuery(
                            "SELECT f FROM Film f WHERE f.id = :id", Film.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Film> findAll() {
        return entityManager.createQuery("SELECT f FROM Film f", Film.class)
                .getResultList();
    }

    @Override
    public Page<Film> findAll(Pageable pageable) {
        Long filmCount;
        try {
            filmCount = entityManager.createQuery("SELECT COUNT(f) FROM Film f", Long.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        var filmList = entityManager.createQuery("SELECT f FROM Film f", Film.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(filmList, pageable, filmCount);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Film film = entityManager.find(Film.class, id);
        if (film != null) {
            entityManager.remove(film);
        }
    }

    @Override
    public List<Film> findTopFilmsByReviewCount(boolean isTop) {
        String query = "SELECT f, COUNT(r) AS reviewCount " +
                "FROM Film f " +
                "LEFT JOIN f.reviewsList r " +
                "GROUP BY f " +
                "ORDER BY reviewCount " + (isTop ? "DESC" : "ASC");
        return entityManager.createQuery(query, Object[].class)
                .getResultList()
                .stream()
                .map(result -> (Film) result[0])
                .collect(Collectors.toList());
    }

    @Override
    public List<Film> findTop5FilmsBySales() {
        return entityManager.createQuery(
                        "SELECT f, COUNT(t) AS ticketCount " +
                                "FROM Ticket t " +
                                "JOIN t.film f " +
                                "GROUP BY f " +
                                "ORDER BY ticketCount DESC",
                        Object[].class)
                .setMaxResults(5)
                .getResultList()
                .stream()
                .map(result -> (Film) result[0])
                .collect(Collectors.toList());
    }

    @Override
    public void update(Film film) {
        entityManager.merge(film);
    }

    @Override
    public Page<Film> findByGenre(String genre, Pageable pageable) {
        Long filmCount = entityManager.createQuery(
                        "SELECT COUNT(f) FROM Film f JOIN f.genres g WHERE g.name = :genre", Long.class)
                .setParameter("genre", genre)
                .getSingleResult();

        List<Film> filmList = entityManager.createQuery(
                        "SELECT f FROM Film f JOIN f.genres g WHERE g.name = :genre ORDER BY f.name", Film.class)
                .setParameter("genre", genre)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(filmList, pageable, filmCount);
    }

    @Override
    public List<Film> findFilmsByActorId(int actorId) {
        return entityManager.createQuery(
                        "SELECT f FROM Film f JOIN f.actors a WHERE a.id = :actorId ORDER BY f.name", Film.class)
                .setParameter("actorId", actorId)
                .getResultList();
    }

    @Override
    public List<Film> findFilmsByDirectorId(int directorId) {
        return entityManager.createQuery(
                        "SELECT f FROM Film f JOIN f.directors d WHERE d.id = :directorId ORDER BY f.name", Film.class)
                .setParameter("directorId", directorId)
                .getResultList();
    }

}