package com.example.OnlineSinema.dto.directorDTO;


import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;

import java.util.List;

public class DirectorsInfoDto {
    private String name;
    private String surname;
    private String midlName;
    private List<String> film;

    public DirectorsInfoDto(String name, String surname, String midlName, List<String> film) {
        this.name = name;
        this.surname = surname;
        this.midlName = midlName;
        this.film = film;
    }

    protected DirectorsInfoDto(){}

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
}
