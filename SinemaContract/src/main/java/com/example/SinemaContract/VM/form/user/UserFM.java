package com.example.SinemaContract.VM.form.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserFM(
        @NotBlank(message = "Name cant be blank")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String name,
        @NotBlank(message = "Email cant be blank")
        @Email(message = "Should be valid")
        String email,
        @NotBlank(message = "Password cant be blank")
        @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
        String password
) {
}
