package com.example.OnlineSinema.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Genres")
public class Genres extends BaseEntity {
    private String genres;
    private List<Film> filmList;

    public Genres(String genres, List<Film> filmList) {
        this.filmList = filmList;
        this.genres = genres;
    }

    public Genres() {}

    public Genres(String genres) {
        this.genres = genres;
    }

    @Column(name = "genres")
    public String getGenres() {
        return genres;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return genres;
    }

    @ManyToMany(mappedBy = "genresList")
    public List<Film> getFilms() {return filmList;}
    public void setFilms(List<Film> films) {this.filmList = filmList;}
}
