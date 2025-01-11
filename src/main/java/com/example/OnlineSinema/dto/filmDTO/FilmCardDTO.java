package com.example.OnlineSinema.dto.filmDTO;

import com.example.OnlineSinema.domain.Genres;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilmCardDTO {
    private int id;
    private String title;
    private LocalDateTime exitDate;
    private Set<Genres> genres = new HashSet<>();
    private double rating;
    private String coment;

    public FilmCardDTO(int id, double rating, Set<Genres> genres, String title,
                       LocalDateTime exitDate, String coment) {
        this.id = id;
        this.rating = rating;
        this.genres = genres != null ? genres : new HashSet<>();
        this.title = title;
        this.exitDate = exitDate;
        this.coment = coment;
    }

    protected FilmCardDTO() {
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
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10.");
        }
        this.rating = rating;
    }

    public Set<Genres> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genres> genres) {
        this.genres = genres != null ? genres : new HashSet<>();
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

    public String getComent() {
        return coment;
    }

    public void setComent(String commnt) {
        this.coment = coment;
    }
}
