package com.example.OnlineSinema.dto.actorsDTO;

import java.util.List;

public class ActorsInfoDto {
    private int id;
    private String name;
    private String surname;
    private String midlName;
    private List<String> film;

    public ActorsInfoDto(int id, String name, String midlName, String surname, List<String> film) {
        this.id = id;
        this.name = name;
        this.midlName = midlName;
        this.surname = surname;
        this.film = film;
    }

    public List<String> getFilm() {
        return film;
    }

    public void setFilm(List<String> film) {
        this.film = film;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMidlName() {
        return midlName;
    }
    public void setMidlName(String midlName) {
        this.midlName = midlName;
    }
}
