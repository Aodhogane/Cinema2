package com.example.SinemaContract.VM.domain.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReviewEditForm(
        @NotNull
        int id,

        @NotBlank(message = "Не должно быть пустым")
        String comment,

        @Positive(message = "Оценка не может быть меньше 1")
        @Max(value = 5, message = "Оценка не может быть больше 5")
        int estimation
) {
}
