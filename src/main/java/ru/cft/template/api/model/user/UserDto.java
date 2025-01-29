package ru.cft.template.api.model.user;

import java.time.LocalDate;

public record UserDto(
        String firstName,
        String lastName,
        String middleName,
        LocalDate birthdate,
        String phone
) {

}
