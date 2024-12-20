package com.example.SinemaContract.VM.domain.actor;

import jakarta.validation.constraints.Min;

public record ActorsSertchForm(
        String title,
        @Min(value = 0, message = "Страница должна быть больше 0")
        Integer page,
        @Min(value = 1, message = "Размер страницы должен быть больше 0")
        Integer size
) {
}
