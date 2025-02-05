package com.example.OnlineSinema.DTO;

import com.example.OnlineSinema.domain.Directors;
import com.example.OnlineSinema.enums.Genres;

import java.time.LocalDateTime;

public class FilmDTO {
    private int id;
    private String title;
    private LocalDateTime exitDate;
    private double rating;
    private String genres;
    private int directorsId;

    public FilmDTO(String title, LocalDateTime exitDate, double rating, String genres, int directorsId) {
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getDirectorsId() {
        return directorsId;
    }

    public void setDirectorsId(int directorsId) {
        this.directorsId = directorsId;
    }
}
