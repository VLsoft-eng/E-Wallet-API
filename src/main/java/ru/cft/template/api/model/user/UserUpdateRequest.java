package ru.cft.template.api.model.user;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UserUpdateRequest(

        @Pattern(regexp = "^[А-ЯЁ][а-яё]+$", message = "Имя должно содержать только буквы русского алфавита и начинаться с заглавной буквы")
        @Size(max = 50, message = "Имя не может быть длиной более 50 символов")
        String firstName,

        @Pattern(regexp = "^[А-ЯЁ][а-яё]+$", message = "Фамилия должна содержать только буквы русского алфавита и начинаться с заглавной буквы")
        @Size(max = 50, message = "Фамилия не может быть длиной более 50 символов")
        String lastName,

        @Pattern(regexp = "^[А-ЯЁ][а-яё]+$", message = "Отчество должно содержать только буквы русского алфавита и начинаться с заглавной буквы")
        @Size(max = 50, message = "Отчество не может быть длиной более 50 символов")
        String middleName,

        @Past(message = "Дата рождения должна быть в прошлом")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate
) {
}
