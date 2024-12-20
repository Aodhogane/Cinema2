package com.example.SinemaContract.VM.form.film;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record FilmFM(
        @NotBlank(message = "Name cant be blank")
        String name,
        @NotNull(message = "Genre list cant be null")
        List<String> genres,
        @NotNull(message = "Actors list cant be null")
        List<String> actors,
        @NotNull(message = "Directors list cant be null")
        List<String> directors,
        LocalDateTime dateTime,
        double reating
) {
}
