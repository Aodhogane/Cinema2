package com.example.OnlineSinema.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reviews")
public class Reviews extends BaseEntity {
    private String comment;
    private int estimation;
    private LocalDateTime dateTime;
    private User user;
    private Film film;

    public Reviews(User user, Film film, String comment, int estimation, LocalDateTime dateTime) {
        this.user = user;
        this.film = film;
        this.comment = comment;
        this.estimation = estimation;
        this.dateTime = dateTime;
    }

    public Reviews() {}

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "estimation")
    public int getEstimation() {
        return estimation;
    }
    public void setEstimation(int estimation) {
        this.estimation = estimation;
    }

    @Column(name = "dateTime")
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "usersId", referencedColumnName = "id")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "filmId", referencedColumnName = "id")
    public Film getFilm() {
        return film;
    }
    public void setFilm(Film film) {
        this.film = film;
    }
}