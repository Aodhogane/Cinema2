package com.example.OnlineSinema.repository.Impl;

import com.example.OnlineSinema.domain.Directors;
import com.example.OnlineSinema.repository.DirectorsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class DirectorRepositoryImpl extends BaseRepository<Directors> implements DirectorsRepository {

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public Directors findDirectorById(int directorId){
        return entityManager.createQuery("select d from Directors d where d.id = :directorId", Directors.class)
                .setParameter("directorId", directorId)
                .getSingleResult();
    }

    @Override
    public Directors findDirectorsByUserId(int userId) {
        return entityManager.createQuery("select d from Directors d where d.user.id = :userId", Directors.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
