package com.example.SinemaContract.VM.form.user;

import jakarta.validation.constraints.NotBlank;

public record UserEditForm (
        @NotBlank(message = "Name cant be blank")
        String name
){
}
