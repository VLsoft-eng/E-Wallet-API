package ru.cft.template.core.dto;

import java.time.LocalDate;

public record UserCreateDto(
        String lastName,
        String firstName,
        String middleName,
        String phoneNumber,
        String email,
        LocalDate birthDate,
        String hashedPassword
) {
}