package com.example.SinemaContract.VM.form.director;

import jakarta.validation.constraints.NotBlank;

public record DirectorFM(
        @NotBlank(message = "Name cant be blank")
        String name,
        @NotBlank(message = "Surname cant be blank")
        String surname,
        @NotBlank(message = "Midllename cant be blank")
        String midlllname
) {
}
