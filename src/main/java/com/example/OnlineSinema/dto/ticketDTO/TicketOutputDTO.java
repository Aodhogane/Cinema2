package com.example.OnlineSinema.dto.ticketDTO;

import java.time.LocalDateTime;

public class TicketOutputDTO {
    private int id;
    private float price;
    private LocalDateTime purchaseDate;
    private int userId;
    private int filmId;

    public TicketOutputDTO(int id, int userId, int filmId, LocalDateTime purchaseDate, float price) {
        this.id = id;
        this.userId = userId;
        this.filmId = filmId;
        this.purchaseDate = purchaseDate;
        this.price = price;
    }

    public TicketOutputDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }
}
