package com.example.OnlineSinema.dto.genresDTO;

public class GenresOutputDTO {
    private int id;
    private String genres;

    public GenresOutputDTO() {}

    public GenresOutputDTO(String genres, int id) {
        this.genres = genres;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
