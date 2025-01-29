package ru.cft.template.api.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Адрес электронной почты не может быть пустым")
        @Email(message = "Адрес электронной почты должен быть в стандартном формате")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        String password
) {
}
