package com.example.OnlineSinema.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Directors")
public class Directors extends BaseEntity {
    private String name;
    private String surname;
    private String midlName;
    private Set<Film> filmsList;
    private User user;

    public Directors(String name, String surname, String midlName, Set<Film> filmsList) {
        this.name = name;
        this.surname = surname;
        this.midlName = midlName;
        this.filmsList = filmsList;
    }

    protected Directors() {}

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

    @OneToMany(mappedBy = "directors", fetch = FetchType.LAZY)
    public Set<Film> getFilmsList() {
        return filmsList;
    }
    public void setFilmsList(Set<Film> filmsList) {
        this.filmsList = filmsList;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
