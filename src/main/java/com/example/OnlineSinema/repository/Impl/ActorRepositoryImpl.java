package com.example.OnlineSinema.repository.Impl;

import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.repository.ActorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorRepositoryImpl extends BaseRepository<Actors> implements ActorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Actors> findActorsByFilmId(int filmId){
        return entityManager.createQuery("SELECT fa.actors FROM FilmActor fa WHERE fa.film.id = :filmId", Actors.class)
                .setParameter("filmId", filmId)
                .getResultList();
    }

    @Override
    public Actors findActorsByUserId(int userId) {
        return entityManager.createQuery("select a from Actors a where a.user.id = :userId", Actors.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
