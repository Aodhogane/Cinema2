package com.example.OnlineSinema.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Directors")
public class Directors extends BaseEntity {

    private String name;
    private String surname;
    private String midlName;
    private List<Film> filmsList;

    public Directors(String name, List<Film> filmsList, String midlName, String surname) {
        this.name = name;
        this.filmsList = filmsList;
        this.midlName = midlName;
        this.surname = surname;
    }

    public Directors() {}

    public Directors(String name, String surname, String midlName) {
        this.name = name;
        this.surname = surname;
        this.midlName = midlName;
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

    @ManyToMany(mappedBy = "directorsList", fetch = FetchType.EAGER)
    public List<Film> getFilms() {return filmsList;}
    public void setFilms(List<Film> films) {this.filmsList = films;}
}
