package com.example.OnlineSinema.dto.filmDTO;

import java.time.LocalDateTime;
import java.util.List;

public class FilmOutputDTO {
    private int id;
    private double rating;
    private int ticketCount;
    private String title;
    private LocalDateTime exitData;
    private List<String> genres;
    private List<String> directors;
    private List<String> actors;

    public FilmOutputDTO() {}

    public FilmOutputDTO(int id, List<String> actors, List<String> genres, List<String> directors, LocalDateTime exitData, String title, int ticketCount, double rating) {
        this.id = id;
        this.actors = actors;
        this.genres = genres;
        this.directors = directors;
        this.exitData = exitData;
        this.title = title;
        this.ticketCount = ticketCount;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public LocalDateTime getExitData() {
        return exitData;
    }

    public void setExitData(LocalDateTime exitData) {
        this.exitData = exitData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}