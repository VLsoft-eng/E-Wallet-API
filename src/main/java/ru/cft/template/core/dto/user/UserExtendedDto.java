package ru.cft.template.core.dto.user;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UserExtendedDto(
        UUID id,
        String firstName,
        String lastName,
        String middleName,
        LocalDate birthdate,
        String email,
        String phone,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
