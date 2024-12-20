package com.example.OnlineSinema.dto.actorsDTO;

import java.util.List;

public class ActorsOutputDTO {
    private int id;
    private String name;
    private String surname;
    private String midlName;
    private List<String> films;

    public ActorsOutputDTO() {}

    public ActorsOutputDTO(int id, String midlName, List<String> films, String surname, String name) {
        this.id = id;
        this.midlName = midlName;
        this.films = films;
        this.surname = surname;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }

    public String getMidlName() {
        return midlName;
    }

    public void setMidlName(String midlName) {
        this.midlName = midlName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}