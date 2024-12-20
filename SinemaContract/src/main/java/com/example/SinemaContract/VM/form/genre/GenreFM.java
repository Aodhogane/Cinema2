package com.example.SinemaContract.VM.form.genre;

import jakarta.validation.constraints.NotBlank;

public record GenreFM(
        @NotBlank(message = "Genre cant be blank")
        String name
) {
}
