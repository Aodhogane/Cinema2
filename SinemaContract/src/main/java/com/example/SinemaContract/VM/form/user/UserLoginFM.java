package com.example.SinemaContract.VM.form.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginFM(
        @NotBlank(message = "Email cant be blank")
        @Email(message = "Should be valid")
        String email,
        @NotBlank(message = "Password cant be blank")
        String password
) {
}
