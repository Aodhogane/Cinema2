package com.example.OnlineSinema.dto.filmDTO;

import com.example.OnlineSinema.domain.Genres;

import java.time.LocalDateTime;
import java.util.List;

public class FilmCardDTO {
    private int id;
    private String title;
    public LocalDateTime exitDate;
    List<Genres> genres;
    double rating;

    public FilmCardDTO(int id, double rating, List<Genres> genres, String title,
                       LocalDateTime exitDate) {
        this.id = id;
        this.rating = rating;
        this.genres = genres;
        this.title = title;
        this.exitDate = exitDate;
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

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
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
}
