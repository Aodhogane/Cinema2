package com.example.OnlineSinema.repository.impl;

import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.domain.Genres;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.repository.FilmRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public List<Film> findByTitleContaining(String titlePart) {
        return entityManager.createQuery(
                        "SELECT f FROM Film f WHERE f.title LIKE :titlePart", Film.class)
                .setParameter("titlePart", "%" + titlePart + "%")
                .getResultList();
    }

    @Override
    public Film findFilmWithDetails(int filmId) {
        try {
            Film film = entityManager.createQuery(
                            "SELECT f FROM Film f " +
                                    "LEFT JOIN FETCH f.actors " +
                                    "LEFT JOIN FETCH f.directors " +
                                    "LEFT JOIN FETCH f.genresList " +
                                    "LEFT JOIN FETCH f.ticketsList " +
                                    "LEFT JOIN FETCH f.reviews " +
                                    "WHERE f.id = :filmId", Film.class)
                    .setParameter("filmId", filmId)
                    .getSingleResult();

            return film;
        } catch (NoResultException e) {
            throw new FilmNotFounf("Film with ID: " + filmId + " not found");
        }
    }

    @Override
    public List<Film> findByGenres(List<Genres> genresList) {
        List<Film> films = entityManager.createQuery(
                        "SELECT DISTINCT f FROM Film f JOIN f.genresList g WHERE g IN :genresList GROUP BY f HAVING COUNT(DISTINCT g) = :genreCount", Film.class)
                .setParameter("genresList", genresList)
                .getResultList();

        return films.stream()
                .filter(f -> f.getGenresList().contains(genresList))
                .collect(Collectors.toList());
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

        List<Film> filmList = entityManager.createQuery(
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
        return entityManager.find(Film.class, id);
    }

    @Override
    @Transactional
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

        List<Film> filmList = entityManager.createQuery("SELECT f FROM Film f", Film.class)
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
    public List<Film> findTop5FilmsBySales() {
        return entityManager.createQuery(
                        "SELECT f FROM Film f " +
                                "JOIN FETCH f.ticketsList t " +
                                "ORDER BY (SELECT COUNT(t) FROM Ticket t WHERE t.film = f) DESC",
                        Film.class)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public List<Film> findByReviewCount() {
        return entityManager.createQuery(
                        "SELECT f " +
                                "FROM Film f " +
                                "JOIN f.reviewsList r " +
                                "GROUP BY f " +
                                "ORDER BY COUNT(r) DESC", Film.class)
                .getResultList();
    }

    @Override
    public Page<Film> findByReviewCount(Pageable pageable) {
        Long filmCount = entityManager.createQuery(
                        "SELECT COUNT(DISTINCT f) FROM Film f JOIN f.reviewsList r", Long.class)
                .getSingleResult();

        List<Film> filmList = entityManager.createQuery(
                        "SELECT f FROM Film f " +
                                "JOIN f.reviewsList r " +
                                "GROUP BY f " +
                                "ORDER BY COUNT(r) DESC", Film.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(filmList, pageable, filmCount);
    }

    @Override
    public Page<Film> findByTitleContainingAndGenres(String filmPart, List<Genres> genresList, Pageable pageable) {
        Long filmCount = entityManager.createQuery(
                        "SELECT COUNT(DISTINCT f) FROM Film f " +
                                "JOIN f.genresList g " +
                                "WHERE f.title LIKE :filmPart AND g IN :genresList", Long.class)
                .setParameter("filmPart", "%" + filmPart + "%")
                .setParameter("genresList", genresList)
                .getSingleResult();

       List<Film> filmsList = (List<Film>) entityManager.createQuery(
               "SELECT DISTINCT f FROM Fil f" +
                       "JOIN f.genresList genresList g" +
                       "WHERE f.title LIKE :filmPart AND g IN" +
                       "IN :genresList", Film.class)
               .setParameter("filmPart", "%" + filmPart + "%")
               .setParameter("genresList", genresList)
               .setFirstResult((int) pageable.getOffset())
               .setMaxResults(pageable.getPageSize())
               .getResultList();

       return new PageImpl<>(filmsList, pageable, filmCount);
    }

    @Override
    public Page<Film> findByTitleContaining(String titlePart, Pageable pageable) {
        Long filmCount;
        try {
            filmCount = entityManager.createQuery(
                            "SELECT COUNT(f) FROM Film f WHERE f.title LIKE :titlePart", Long.class)
                    .setParameter("titlePart", "%" + titlePart + "%")
                    .getSingleResult();
        } catch (NoResultException e) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<Film> filmList = entityManager.createQuery(
                        "SELECT f FROM Film f WHERE f.title LIKE :titlePart", Film.class)
                .setParameter("titlePart", "%" + titlePart + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(filmList, pageable, filmCount);
    }

    @Override
    @Transactional
    public void update(Film film) {
        entityManager.merge(film);
    }

    @Override
    public List<Film> findFilmsByActorId(int actorId) {
        return entityManager.createQuery(
                        "SELECT f FROM Film f JOIN f.actorsList a WHERE a.id = :actorId", Film.class)
                .setParameter("actorId", actorId)
                .getResultList();
    }

    @Override
    public List<Film> findFilmsByDirectorId(int directorId) {
        return entityManager.createQuery(
                        "SELECT f FROM Film f JOIN f.directorsList d WHERE d.id = :directorId", Film.class)
                .setParameter("directorId", directorId)
                .getResultList();
    }
}