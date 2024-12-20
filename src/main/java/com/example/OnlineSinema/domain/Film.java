package com.example.OnlineSinema.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Films")
public class Film extends BaseEntity {

    private String title;
    private LocalDateTime exitDate;
    private Integer duration;
    private double rating;
    private List<Actors> actorsList;
    private List<Directors> directorsList;
    private List<Genres> genresList;
    private List<Reviews> reviewsList;
    private List<Ticket> ticketsList;

    public Film(String title, LocalDateTime exitDate, int duration,
                List<Genres> genres, List<Directors> directorsList,
                List<Actors> actorsList, List<Reviews> reviews,
                double rating) {
        this.title = title;
        this.exitDate = exitDate;
        this.duration = duration;
        this.genresList = genres;
        this.directorsList = directorsList;
        this.actorsList = actorsList;
        this.reviewsList = reviews;
        this.rating = rating;
    }

    public Film() {}

    @Column(name = "title")
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "exitDate")
    public LocalDateTime getExitDate() {
        return exitDate;
    }
    public void setExitDate(LocalDateTime exitDate) {
        this.exitDate = exitDate;
    }

    @Column(name = "duration")
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Column(name = "rating")
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    @ManyToMany
    @JoinTable(
            name = "FilmActors",
            joinColumns = @JoinColumn(name = "filmId"),
            inverseJoinColumns = @JoinColumn(name = "actorId")
    )
    public List<Actors> getActors() {
        return actorsList;
    }
    public void setActors(List<Actors> actors) {
        this.actorsList = actors;
    }

    @ManyToMany
    @JoinTable(
            name = "FilmDirector",
            joinColumns = @JoinColumn(name = "filmId"),
            inverseJoinColumns = @JoinColumn(name = "directorId")
    )
    public List<Directors> getDirectors() {
        return directorsList;
    }
    public void setDirectors(List<Directors> directors) {
        this.directorsList = directors;
    }

    @ManyToMany
    @JoinTable(
            name = "FilmGenre",
            joinColumns = @JoinColumn(name = "filmId"),
            inverseJoinColumns = @JoinColumn(name = "genreId")
    )
    public List<Genres> getGenres() {
        return genresList;
    }
    public void setGenres(List<Genres> genres) {
        this.genresList = genres;
    }

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Reviews> getReviews() {
        return reviewsList;
    }
    public void setReviews(List<Reviews> reviews) {
        this.reviewsList = reviews;
    }

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Ticket> getTicketsList() {
        return ticketsList;
    }
    public void setTicketsList(List<Ticket> ticketsList) {
        this.ticketsList = ticketsList;
    }
}