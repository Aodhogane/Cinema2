package com.example.SinemaContract.VM.form.actor;

import jakarta.validation.constraints.NotBlank;

public record ActorFM (
        @NotBlank(message = "Name cant be blank")
        String name,
        @NotBlank(message = "Surname cant be blank")
        String surname,
        @NotBlank(message = "Midllename cant be blank")
        String midlllname
){
}
