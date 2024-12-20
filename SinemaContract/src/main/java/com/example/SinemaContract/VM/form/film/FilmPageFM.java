package com.example.SinemaContract.VM.form.film;

import jakarta.validation.constraints.Min;

public record FilmPageFM(
        @Min(value = 0, message = "Page cannot be less than 0")
        Integer page,
        @Min(value = 1, message = "Page size must be greater than 0")
        Integer size
) {
}
