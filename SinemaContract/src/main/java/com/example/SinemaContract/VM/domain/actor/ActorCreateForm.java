package com.example.SinemaContract.VM.domain.actor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ActorCreateForm (

        @NotBlank(message = "Название актёры не могут быть пусты")
        @Size(min = 2, message = "название должно содержать 2 символа")
        String name,

        @NotNull(message = "Id фильма не должен быть пустым")
        int filmId
){
}