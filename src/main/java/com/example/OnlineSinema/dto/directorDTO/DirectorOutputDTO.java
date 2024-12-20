package com.example.OnlineSinema.dto.directorDTO;
import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;

import java.util.List;

public class DirectorOutputDTO {
    private int id;
    private String name;
    private String surname;
    private String midlName;
    private List<String> films;

    public DirectorOutputDTO() {}

    public DirectorOutputDTO(int id, List<String> films, String midlName, String surname, String name) {
        this.id = id;
        this.films = films;
        this.midlName = midlName;
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