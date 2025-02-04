package com.example.OnlineSinema.repository.Impl;

import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.domain.FilmActor;
import com.example.OnlineSinema.repository.FilmActorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmActorRepositoryImpl extends BaseRepository<FilmActor> implements FilmActorRepository {

    @PersistenceContext
    private EntityManager entityManager;
}
