package com.example.OnlineSinema.dto.reviewDTO;

import java.time.LocalDateTime;

public class ReviewOutputDTO {
    private int id;
    private int userId;
    private Long filmId;
    private int estimation;
    private String comment;
    private String userName;
    private String filmName;
    private LocalDateTime dateTime;

    public ReviewOutputDTO(int id, int userId, Long filmId, int estimation, String comment, String userName, String filmName, LocalDateTime dateTime) {
        this.id = id;
        this.userId = userId;
        this.filmId = filmId;
        this.estimation = estimation;
        this.comment = comment;
        this.userName = userName;
        this.filmName = filmName;
        this.dateTime = dateTime;
    }

    public ReviewOutputDTO() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}