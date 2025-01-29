package ru.cft.template.api.model.auth;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record RegistrationRequest(
        @NotBlank(message = "Фамилия не может быть пустой")
        @Pattern(regexp = "^[А-ЯЁ][а-яё]+$", message = "Фамилия должна содержать только буквы русского алфавита и начинаться с заглавной буквы")
        @Size(max = 50, message = "Фамилия не может быть длиной более 50 символов")
        String lastName,

        @NotBlank(message = "Имя не может быть пустым")
        @Pattern(regexp = "^[А-ЯЁ][а-яё]+$", message = "Имя должно содержать только буквы русского алфавита и начинаться с заглавной буквы")
        @Size(max = 50, message = "Имя не может быть длиной более 50 символов")
        String firstName,

        @Pattern(regexp = "^[А-ЯЁ][а-яё]+$", message = "Отчество должно содержать только буквы русского алфавита и начинаться с заглавной буквы")
        @Size(max = 50, message = "Отчество не может быть длиной более 50 символов")
        String middleName,

        @NotBlank(message = "Номер мобильного телефона не может быть пустым")
        @Pattern(regexp = "^7\\d{10}$", message = "Номер телефона должен состоять из 11 цифр и начинаться с '7'")
        String phoneNumber,

        @NotBlank(message = "Электронная почта не может быть пустой")
        @Email(message = "Адрес электронной почты должен быть в стандартном формате")
        @Size(max = 100, message = "Электронная почта не может быть длиной более 100 символов")
        String email,

        @NotNull(message = "Дата рождения не может быть пустой")
        @Past(message = "Дата рождения должна быть в прошлом")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 8, max = 64, message = "Пароль должен быть от 8 до 64 символов")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!?.]).+$", message = "Пароль должен содержать хотя бы одну букву верхнего регистра, одну букву нижнего регистра, одну цифру и один спецсимвол (!, ?, .)")
        String password
) {
}

