package ru.cft.template.api.model.auth;

import java.util.UUID;

public record RegistrationResponse(
        UUID userId
) {
}
