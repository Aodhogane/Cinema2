package com.example.OnlineSinema.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "filmActor")
public class FilmActor extends BaseEntity{

    private Film film;
    private Actors actors;

    public FilmActor(Film film, Actors actors) {
        this.film = film;
        this.actors = actors;
    }

    protected FilmActor(){}

    @ManyToOne(optional = false)
    @JoinColumn(name = "film_id", referencedColumnName = "id")
    public Film getFilm() {
        return film;
    }
    public void setFilm(Film film) {
        this.film = film;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "actor_id", referencedColumnName = "id")
    public Actors getActors() {
        return actors;
    }
    public void setActors(Actors actors) {
        this.actors = actors;
    }
}
