package com.example.OnlineSinema.DTO;

import java.time.LocalDateTime;

public class ReviewDTO {
    private int id;
    private String comment;
    private int estimation;
    private LocalDateTime dateTime;
    private int clientId;
    private int filmId;

    public ReviewDTO(String comment, int estimation, LocalDateTime dateTime) {
        this.comment = comment;
        this.estimation = estimation;
        this.dateTime = dateTime;
    }

    public ReviewDTO(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getEstimation() {
        return estimation;
    }

    public void setEstimation(int estimation) {
        this.estimation = estimation;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }
}
