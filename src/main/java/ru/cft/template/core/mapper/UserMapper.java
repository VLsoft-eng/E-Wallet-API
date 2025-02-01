package ru.cft.template.core.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.core.dto.UserCreateDto;
import ru.cft.template.api.model.user.UserDto;
import ru.cft.template.core.entity.User;

@Component
public class UserMapper {
    public UserDto toUserShortDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getBirthdate(),
                null,
                user.getPhone(),
                null,
                null
        );
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getBirthdate(),
                user.getEmail(),
                user.getPhone(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User toUser(UserCreateDto userCreateDto) {
        return User.builder()
                .firstName(userCreateDto.firstName())
                .lastName(userCreateDto.lastName())
                .middleName(userCreateDto.middleName())
                .phone(userCreateDto.phoneNumber())
                .email(userCreateDto.email())
                .birthdate(userCreateDto.birthDate())
                .hashedPassword(userCreateDto.hashedPassword())
                .build();
    }
}
