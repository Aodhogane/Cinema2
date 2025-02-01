package com.example.OnlineSinema.DTO;

import com.example.OnlineSinema.domain.Directors;
import com.example.OnlineSinema.enums.Genres;

import java.time.LocalDateTime;

public class FilmDTO {
    private int id;
    private String title;
    private LocalDateTime exitDate;
    private double rating;
    private Genres genres;
    private int directorsId;
    private int actorId;

    public FilmDTO(String title, LocalDateTime exitDate, double rating, Genres genres, int directorsId) {
        this.title = title;
        this.exitDate = exitDate;
        this.rating = rating;
        this.genres = genres;
        this.directorsId = directorsId;
    }
    public FilmDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDateTime exitDate) {
        this.exitDate = exitDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Genres getGenres() {
        return genres;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }

    public int getDirectorsId() {
        return directorsId;
    }

    public void setDirectorsId(int directorsId) {
        this.directorsId = directorsId;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }
}
