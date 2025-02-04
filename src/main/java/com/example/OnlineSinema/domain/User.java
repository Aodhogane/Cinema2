package com.example.OnlineSinema.domain;

import com.example.OnlineSinema.enums.UserRoles;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Users")
public class User extends BaseEntity {

    private String email;
    private String password;
    private UserRoles userRoles;
    private Client client;
    private Actors actors;
    private Directors directors;

    protected User() {}

    public User(String email, String password, UserRoles userRoles) {
        this.email = email;
        this.password = password;
        this.userRoles = userRoles;
    }

    @Column(name = "username", nullable = false)
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    public UserRoles getUserRoles() {
        return userRoles;
    }
    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    public Actors getActors() {return actors;}
    public void setActors(Actors actors) {this.actors = actors;}

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    public Directors getDirectors() {return directors;}
    public void setDirectors(Directors directors) {this.directors = directors;}
}