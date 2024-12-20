package com.example.SinemaContract.VM.domain.director;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DirectorEditForm (
        @NotNull(message = "ID не может быть пустми")
        int id,

        @NotBlank(message = "Название актёры не могут быть пусты")
        @Size(min = 2, message = "название должно содержать 2 символа")
        String name,

        @NotNull(message = "Id фильма не должен быть пустым")
        int filmId
){
}
