package com.example.SinemaContract.VM.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateForm(
        @NotBlank(message = "Имя не может быть пустым")
        @Size(min = 2, message = "Имя должно содержать минимум 2 символа")
        String name,

        @NotBlank(message = "Email не должна быть пуста")
        @Email(message = "Email должна быть в правильной форме")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 8, max = 16, message = "Пароль должен содержать от 8 до 16 символов")
        String password
) {
}
