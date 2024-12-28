package com.example.OnlineSinema.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Films")
public class Film extends BaseEntity {

    private String title;
    private LocalDateTime exitDate;
    private Integer duration;
    private double rating;
    private Set<Actors> actorsList;
    private Set<Directors> directorsList;
    private Set<Genres> genresList;
    private Set<Reviews> reviewsList;
    private Set<Ticket> ticketsList;

    public Film(String title, LocalDateTime exitDate, int duration,
                Set<Genres> genres, Set<Directors> directorsList,
                Set<Actors> actorsList, Set<Reviews> reviews,
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "FilmActors",
            joinColumns = @JoinColumn(name = "filmId"),
            inverseJoinColumns = @JoinColumn(name = "actorId")
    )
    public Set<Actors> getActors() {
        return actorsList;
    }
    public void setActors(Set<Actors> actors) {
        this.actorsList = actors;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "FilmDirector",
            joinColumns = @JoinColumn(name = "filmId"),
            inverseJoinColumns = @JoinColumn(name = "directorId")
    )
    public Set<Directors> getDirectors() {
        return directorsList;
    }
    public void setDirectors(Set<Directors> directors) {
        this.directorsList = directors;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "FilmGenre",
            joinColumns = @JoinColumn(name = "filmId"),
            inverseJoinColumns = @JoinColumn(name = "genreId")
    )
    public Set<Genres> getGenresList() {
        return genresList;
    }
    public void setGenresList(Set<Genres> genres) {
        this.genresList = genres;
    }

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Reviews> getReviews() {
        return reviewsList;
    }
    public void setReviews(Set<Reviews> reviews) {
        this.reviewsList = reviews;
    }

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Ticket> getTicketsList() {
        return ticketsList;
    }
    public void setTicketsList(Set<Ticket> ticketsList) {
        this.ticketsList = ticketsList;
    }
}