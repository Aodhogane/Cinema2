package com.example.OnlineSinema.repository.impl;

import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.repository.ActorRepository;
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
public class ActorRepositoryImpl implements ActorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Actors actor) {
        if (entityManager.contains(actor)) {
            entityManager.merge(actor);
        } else {
            entityManager.persist(actor);
        }
    }

    @Override
    public Actors findById(int id) {
        try {
            return entityManager.createQuery("SELECT a FROM Actors a WHERE a.id = :id", Actors.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Actors findByName(String name, String surname, String middleName) {
        try {
            return entityManager.createQuery(
                            "SELECT a FROM Actors a WHERE LOWER(a.name) = LOWER(:name) AND LOWER(a.surname) = LOWER(:surname) AND LOWER(a.middleName) = LOWER(:middleName)", Actors.class)
                    .setParameter("name", name)
                    .setParameter("surname", surname)
                    .setParameter("middleName", middleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Actors> findAll() {
        return entityManager.createQuery("SELECT a FROM Actors a", Actors.class).getResultList();
    }

    @Override
    public List<Actors> findByFilmId(int id) {
        return entityManager.createQuery(
                        "SELECT a FROM Actors a JOIN a.filmsList f WHERE f.id = :filmId", Actors.class)
                .setParameter("filmId", id)
                .getResultList();
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Actors actor = findById(id);
        if (actor != null) {
            entityManager.remove(actor);
        }
    }

    @Override
    public Page<Actors> findAll(Pageable pageable) {
        List<Actors> actors = entityManager.createQuery("SELECT a FROM Actors a", Actors.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery("SELECT COUNT(a) FROM Actors a", Long.class)
                .getSingleResult();

        return new PageImpl<>(actors, pageable, total);
    }

    @Override
    @Transactional
    public void update(Actors actor) {
        entityManager.merge(actor);
    }
}