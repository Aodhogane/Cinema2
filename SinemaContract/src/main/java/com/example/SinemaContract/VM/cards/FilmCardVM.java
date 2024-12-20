package com.example.SinemaContract.VM.cards;

import java.time.LocalDateTime;
import java.util.List;

public record FilmCardVM(
        int filmId,
        String nameFilm,
        List<String> genres,
        List<String> actors,
        List<String> directors,
        double rating,
        LocalDateTime dataTime
){

}
