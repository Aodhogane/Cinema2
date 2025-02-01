package com.example.OnlineSinema.domain;

import com.example.OnlineSinema.enums.Genres;
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
    private double rating;
    private Directors directors;
    private Genres genres;
    private Set<Reviews> reviewsList;
    private Set<FilmActor> filmActors;

    public Film(String title, LocalDateTime exitDate, double rating,
                Directors directors, Genres genres,
                Set<Reviews> reviewsList, Set<FilmActor> filmActors) {
        this.title = title;
        this.exitDate = exitDate;
        this.rating = rating;
        this.directors = directors;
        this.genres = genres;
        this.reviewsList = reviewsList;
        this.filmActors = filmActors;
    }

    protected Film() {}

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

    @Column(name = "rating")
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    public Genres getGenres() {
        return genres;
    }
    public void setGenres(Genres genres) {
        this.genres = genres;
    }

    @OneToMany(mappedBy = "film", fetch = FetchType.LAZY)
    public Set<FilmActor> getFilmActors() {
        return filmActors;
    }
    public void setFilmActors(Set<FilmActor> filmActors) {
        this.filmActors = filmActors;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "directors_id", referencedColumnName = "id")
    public Directors getDirectors() {return directors;}
    public void setDirectors(Directors directors) {this.directors = directors;}

    @OneToMany(mappedBy = "film", fetch = FetchType.LAZY)
    public Set<Reviews> getReviewsList() {return reviewsList;}
    public void setReviewsList(Set<Reviews> reviewsList) {this.reviewsList = reviewsList;}
}