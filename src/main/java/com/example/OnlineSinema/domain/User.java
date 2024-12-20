package com.example.OnlineSinema.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
public class User extends BaseEntity {

    private String name;
    private String email;
    private String password;
    private List<Access> access;
    private List<Reviews> reviewsList;
    private List<Ticket> ticketsList;

    public User() {
        this.access = new ArrayList<>();
    }

    public User(String name, List<Access> access, String email, String password, List<Reviews> reviewsList, List<Ticket> ticketsList) {
        this();

        this.name = name;
        this.access = access;
        this.email = email;
        this.password = password;
        this.reviewsList = reviewsList;
        this.ticketsList = ticketsList;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public List<Access> getAccess() {
        return access;
    }
    public void setAccess(List<Access> access) {
        this.access = access;
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
}