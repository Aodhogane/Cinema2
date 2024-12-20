package com.example.OnlineSinema.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Actors")
public class Actors extends BaseEntity {

    private String name;
    private String surname;
    private String midlName;
    private List<Film> filmsList;

    public Actors(String name, String midlName, String surname, List<Film> filmsList) {
        this.name = name;
        this.midlName = midlName;
        this.surname = surname;
        this.filmsList = filmsList;
    }

    protected Actors() {}

    public Actors(String name, String surname, String midlName) {
        this.name = name;
        this.midlName = midlName;
        this.surname = surname;
    }

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

    @ManyToMany(mappedBy = "actors")
    public List<Film> getFilms() {return filmsList;}
    public void setFilms(List<Film> films) {
        this.filmsList = films;
    }
}
