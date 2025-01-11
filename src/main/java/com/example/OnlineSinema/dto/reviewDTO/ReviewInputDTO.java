package com.example.OnlineSinema.dto.reviewDTO;

public class ReviewInputDTO {
    private int filmId;
    private int userId;
    private String comment;
    private int estimation;

    public ReviewInputDTO(int filmId, int userId, String comment, int estimation) {
        this.filmId = filmId;
        this.userId = userId;
        this.comment = comment;
        this.estimation = estimation;
    }

    public ReviewInputDTO() {}

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
