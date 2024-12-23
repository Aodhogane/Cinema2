package com.example.OnlineSinema.repository.impl;

import com.example.OnlineSinema.domain.Access;
import com.example.OnlineSinema.repository.AccessRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccessRepositoryImpl implements AccessRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Access findByName(String registered) {
        try {
            return entityManager.createQuery("SELECT a FROM Access a WHERE a.registered = :access", Access.class)
                    .setParameter("access", registered)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Access role) {
        if (entityManager.contains(role)){
            entityManager.merge(role);
        }else{
            entityManager.persist(role);
        }
    }

    @Override
    public List<Access> findAll() {
        return entityManager.createQuery("SELECT a FROM Access a", Access.class)
                .getResultList();
    }

    @Override
    public Access findById(int id) {
        try {
            return entityManager.createQuery("SELECT a FROM Access a WHERE a.id = :id", Access.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
