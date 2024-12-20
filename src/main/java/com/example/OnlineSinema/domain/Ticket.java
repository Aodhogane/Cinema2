package com.example.OnlineSinema.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Ticket")
public class Ticket extends BaseEntity {

    private float price;
    private LocalDateTime purchaseDate;
    private User user;
    private Film film;

    public Ticket(float price, LocalDateTime purchaseDate, User user, Film film) {
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.user = user;
        this.film = film;
    }

    public Ticket() {}

    @Column(name = "price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @Column(name = "purchaseDate")
    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", referencedColumnName = "id")
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