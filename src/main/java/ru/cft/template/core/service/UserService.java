package ru.cft.template.core.service;

import ru.cft.template.api.model.user.UserDto;
import ru.cft.template.api.model.user.UserUpdateRequest;
import ru.cft.template.core.dto.UserCreateDto;

import java.util.UUID;

public interface UserService {
    public UUID createUser(UserCreateDto userCreateDto);

    public void updateUser(UUID userId, UserUpdateRequest userUpdateRequest);

    public UserDto getUser(UUID userId);
}
