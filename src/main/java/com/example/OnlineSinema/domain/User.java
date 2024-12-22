package com.example.OnlineSinema.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Users")
public class User extends BaseEntity {

    private String username;
    private String email;
    private String password;
    private Access access;
    private List<Reviews> reviewsList;
    private List<Ticket> ticketsList;

    public User() {
    }

    public User(String username, Access access, String email, String password, List<Reviews> reviewsList, List<Ticket> ticketsList) {
        this();

        this.username = username;
        this.access = access;
        this.email = email;
        this.password = password;
        this.reviewsList = reviewsList;
        this.ticketsList = ticketsList;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "Email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<Reviews> getReviewsList() {
        return reviewsList;
    }
    public void setReviewsList(List<Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<Ticket> getTicketsList() {
        return ticketsList;
    }
    public void setTicketsList(List<Ticket> ticketsList) {
        this.ticketsList = ticketsList;
    }

    @ManyToOne
    @JoinColumn(name = "accessId")
    public Access getAccess() {
        return access;
    }
    public void setAccess(Access access) {
        this.access = access;
    }
}