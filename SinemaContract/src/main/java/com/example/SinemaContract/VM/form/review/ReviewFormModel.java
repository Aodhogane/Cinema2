package com.example.SinemaContract.VM.form.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewFormModel(
        @NotNull(message = "User ID cant be null")
        int userId,
        @NotNull(message = "User name cant be null")
        String name,
        @NotNull(message = "Film Id cant be null")
        int filmId,
        @NotNull(message = "Film title cant be null")
        String nameFilm,
        @NotNull(message = "Rating cant be null")
        @Min(value = 0)
        @Max(value = 5)
        int rating,
        @NotBlank(message = "Text cant be blank")
        String text
) {
}
