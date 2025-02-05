package com.example.OnlineSinema.DTO;

public class FilmActorDTO {
    private int id;
    private int filmId;
    private int actorId;

    public FilmActorDTO(int filmId, int actorId) {
        this.filmId = filmId;
        this.actorId = actorId;
    }

    public FilmActorDTO() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getActorId() {
        return actorId;
    }
    public void setActorId(int actorId) {
        this.actorId = actorId;
    }
}
