package com.example.SinemaContract.VM.domain.film;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public record FilmCreateForm(
        @NotBlank(message = "Название обязательно") String title,
        @NotEmpty (message = "Актёр обязателен") List<String> actor,
        @NotEmpty(message = "Режисёр обязателен") List<String> director,
        @NotEmpty (message = "Жанр обязателен") List<String> genre,
        @NotBlank(message = "Дата не должна быть пуста") LocalDateTime dateTime
        ) {
}
