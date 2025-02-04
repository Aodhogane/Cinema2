package com.example.OnlineSinema.DTO.inputDTO;

import com.example.OnlineSinema.enums.Genres;

import java.time.LocalDateTime;

public class FilmInputDTO {

    private String title;
    private LocalDateTime exitDate;
    private double rating;
    private String genres;
    private int directorId;

    public FilmInputDTO(String title, LocalDateTime exitDate, double rating, String genres, int directorId) {
        this.title = title;
        this.exitDate = exitDate;
        this.rating = rating;
        this.genres = genres;
        this.directorId = directorId;
    }

    private FilmInputDTO(){}

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

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }
}
