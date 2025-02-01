package com.example.OnlineSinema.repository.Impl;

import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.repository.ActorRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorRepositoryImpl extends BaseRepository<Actors> implements ActorRepository {

    @Override
    public List<Actors> findActorsByFilmId(int filmId){
        return entityManager.createQuery("select a from FilmActorRepository fa join fa.actors a where fa.film.id =: filmId", Actors.class)
                .setParameter("filmId", filmId)
                .getResultList();
    }

    @Override
    public Actors findActorById(int actorId){
        return entityManager.createQuery("select a from Actors a where a.id =: actorId", Actors.class)
                .setParameter("actorId", actorId)
                .getSingleResult();
    }
}
