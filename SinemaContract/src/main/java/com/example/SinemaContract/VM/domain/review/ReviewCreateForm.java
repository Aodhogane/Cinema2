package com.example.SinemaContract.VM.domain.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ReviewCreateForm(
        @NotBlank(message = "Не должно быть пустым")
        String comment,

        @Positive(message = "Оценка не может быть меньше нуля")
        @Max(value = 5, message = "Оценка не может быть больше 5")
        int estimation,

        LocalDateTime dateTime,

        @NotNull
        int filmId,

        @NotNull
        int userId
) {
}
