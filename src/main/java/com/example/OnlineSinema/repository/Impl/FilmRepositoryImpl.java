package com.example.OnlineSinema.repository.Impl;

import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.repository.FilmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmRepositoryImpl extends BaseRepository<Film> implements FilmRepository {

    @Override
    public Page<Film> findFilmByTitle(String title, int page, int size){
        long total = entityManager.createQuery("select count(f) from Film f where f.title like : title", Long.class)
                .setParameter("title", "%" + title + "%")
                .getSingleResult();

        List<Film> entities = entityManager.createQuery("select f from Film f where f.title like : title", Film.class)
                .setFirstResult((page - 1) * size)
                .setParameter("title", "%" + title + "%")
                .setMaxResults(size)
                .getResultList();

        return new PageImpl<>(entities, PageRequest.of(page - 1, size), total);
    }

    @Override
    public Page<Film> findFilmByGenres(String genres, int page, int size){
        long total = entityManager.createQuery("select count(f) from Film f where f.genres =: genres", Long.class)
                .setParameter("genres", genres)
                .getSingleResult();

        List<Film> entities = entityManager.createQuery("select f from Film f where f.genres =: genres", Film.class)
                .setFirstResult((page - 1) * size)
                .setParameter("genres", genres)
                .setMaxResults(size)
                .getResultList();

        return new PageImpl<>(entities, PageRequest.of(page - 1, size), total);
    }

    @Override
    public List<Film> findFilmByActorsId(int actorsId){
        return entityManager.createQuery("select f from FilmActorRepository fa join fa.film f where fa.actors.id = :actorsId", Film.class)
                .setParameter("actorsId", actorsId)
                .getResultList();
    }

    @Override
    public List<Film> findFilmsByDirectorsId(int directorsId){
        return entityManager.createQuery("select f from Film f where f.directors.id = :directorsId", Film.class)
                .setParameter("directorsId", directorsId)
                .getResultList();
    }
}
