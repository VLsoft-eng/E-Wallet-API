package ru.cft.template.core.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.api.model.auth.RegistrationRequest;
import ru.cft.template.core.dto.UserCreateDto;

@Component
public class RegistrationMapper {
    public UserCreateDto toUserCreateDto(RegistrationRequest request, String hashedPassword) {
        return new UserCreateDto(
                request.lastName(),
                request.firstName(),
                request.middleName(), request.phoneNumber(),
                request.email(),
                request.birthdate(),
                hashedPassword);
    }
}
