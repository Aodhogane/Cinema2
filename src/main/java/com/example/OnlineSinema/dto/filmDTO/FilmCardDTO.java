package com.example.OnlineSinema.dto.filmDTO;

import com.example.OnlineSinema.domain.Genres;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class FilmCardDTO {
    private int id;
    private String title;
    private LocalDateTime exitDate;
    Set<Genres> genres;
    double rating;
    private String comet;

    public FilmCardDTO(int id, double rating, Set<Genres> genres, String title,
                       LocalDateTime exitDate, String comet) {
        this.id = id;
        this.rating = rating;
        this.genres = genres;
        this.title = title;
        this.exitDate = exitDate;
        this.comet = comet;
    }

    protected FilmCardDTO(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Set<Genres> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genres> genres) {
        this.genres = genres;
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

    public String getComet() {
        return comet;
    }

    public void setComet(String comet) {
        this.comet = comet;
    }
}
