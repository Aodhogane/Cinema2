package com.example.OnlineSinema.domain;

import com.example.OnlineSinema.enums.UserRoles;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Actors")
public class Actors extends BaseEntity {

    private String name;
    private String surname;
    private String midlName;
    private Set<FilmActor> filmActors;
    private User user;

    public Actors(String name, String surname, String midlName, Set<FilmActor> filmActors, User user) {
        this.name = name;
        this.surname = surname;
        this.midlName = midlName;
        this.filmActors = filmActors;
        this.user = user;
    }

    protected Actors() {}

    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "midlName")
    public String getMidlName() {
        return midlName;
    }
    public void setMidlName(String midlName) {
        this.midlName = midlName;
    }

    @OneToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    public Set<FilmActor> getFilmActors() {return filmActors;}
    public void setFilmActors(Set<FilmActor> filmActors) {this.filmActors = filmActors;}

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
