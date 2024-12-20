package com.example.SinemaContract.VM.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserEditForm(

        @NotBlank(message = "Имя не может быть пустым")
        String name
){}
