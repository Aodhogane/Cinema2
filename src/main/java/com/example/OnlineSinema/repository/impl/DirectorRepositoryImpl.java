package com.example.OnlineSinema.repository.impl;

import com.example.OnlineSinema.domain.Directors;
import com.example.OnlineSinema.repository.DirectorRepository;
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
public class DirectorRepositoryImpl implements DirectorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Directors director) {
        if (entityManager.contains(director)) {
            entityManager.merge(director);
        } else {
            entityManager.persist(director);
        }
    }

    @Override
    public Directors findById(int id) {
        try {
            return entityManager.createQuery("SELECT d FROM Directors d WHERE d.id = :id", Directors.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Directors findByName(String name, String surname, String middleName) {
        try {
            return entityManager.createQuery(
                            "SELECT d FROM Directors d WHERE LOWER(d.name) = LOWER(:name) AND LOWER(d.surname) = LOWER(:surname) AND LOWER(d.middleName) = LOWER(:middleName)", Directors.class)
                    .setParameter("name", name)
                    .setParameter("surname", surname)
                    .setParameter("middleName", middleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Directors> findAll() {
        return entityManager.createQuery("SELECT d FROM Directors d", Directors.class).getResultList();
    }

    @Override
    public Page<Directors> findAll(Pageable pageable) {
        Long total = entityManager.createQuery("SELECT COUNT(d) FROM Directors d", Long.class).getSingleResult();
        List<Directors> directors = entityManager.createQuery("SELECT d FROM Directors d", Directors.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(directors, pageable, total);
    }

    @Override
    public List<Directors> findByFilmId(int id) {
        return entityManager.createQuery(
                        "SELECT d FROM Directors d JOIN d.filmsList f WHERE f.id = :filmId", Directors.class)
                .setParameter("filmId", id)
                .getResultList();
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Directors director = findById(id);
        if (director != null) {
            entityManager.remove(director);
        }
    }

    @Override
    @Transactional
    public void update(Directors directors) {
        entityManager.merge(directors);
    }

}