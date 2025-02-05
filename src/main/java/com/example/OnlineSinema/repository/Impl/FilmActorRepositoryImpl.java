package com.example.OnlineSinema.repository.Impl;

import com.example.OnlineSinema.domain.FilmActor;
import com.example.OnlineSinema.repository.FilmActorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class FilmActorRepositoryImpl extends BaseRepository<FilmActor> implements FilmActorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public FilmActor findFilmActorByFilmIdAndActorId(int filmId, int actorId) {
        return entityManager.createQuery("select fa from FilmActor fa where fa.film.id = :filmId and fa.actors.id = :actorId", FilmActor.class)
                .setParameter("filmId", filmId)
                .setParameter("actorId", actorId)
                .getSingleResult();
    }
}
