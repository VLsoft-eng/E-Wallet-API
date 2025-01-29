package ru.cft.template.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.cft.template.core.dto.UserCreateDto;
import ru.cft.template.api.model.user.UserDto;
import ru.cft.template.api.model.user.UserExtendedDto;
import ru.cft.template.api.model.user.UserUpdateRequest;
import ru.cft.template.core.entity.User;
import ru.cft.template.core.exception.CredentialAlreadyUsedException;
import ru.cft.template.core.exception.DoesntHaveRightsException;
import ru.cft.template.core.exception.ExceptionMessage;
import ru.cft.template.core.exception.UserNotFoundException;
import ru.cft.template.core.mapper.UserMapper;
import ru.cft.template.core.repository.UserRepository;
import ru.cft.template.core.security.userDetails.CustomUserDetails;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UUID createUser(UserCreateDto userCreateDto) {
        if (userRepository.existsByPhone(userCreateDto.phoneNumber())) {
            log.warn("Registration rejected. Phone number already used");
            throw new CredentialAlreadyUsedException(ExceptionMessage.PHONE_ALREADY_USED_MESSAGE);
        }

        if (userRepository.existsByEmail(userCreateDto.email())) {
            log.warn("Registration rejected. Email already used");
            throw new CredentialAlreadyUsedException(ExceptionMessage.EMAIL_ALREADY_USED_MESSAGE);
        }

        User user = userMapper.toUser(userCreateDto);
        userRepository.save(user);

        log.info("User created");

        return user.getId();
    }

    public void updateUser(UUID userId, UserUpdateRequest userUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (!userDetails.getId().equals(userId)) {
            log.warn("Updating user with id{} rejected - doesnt have rights", userId);
            throw new DoesntHaveRightsException(ExceptionMessage.EDIT_OTHER_USER_NOT_ALLOWED_MESSAGE);
        }

        user.setFirstName(userUpdateRequest.firstName() != null ? userUpdateRequest.firstName() : user.getFirstName());
        user.setLastName(userUpdateRequest.lastName() != null ? userUpdateRequest.lastName() : user.getLastName());
        user.setMiddleName(userUpdateRequest.middleName() != null ? userUpdateRequest.middleName() : user.getMiddleName());
        user.setBirthdate(userUpdateRequest.birthdate() != null ? userUpdateRequest.birthdate() : user.getBirthdate());

        userRepository.save(user);
        log.info("User with id{} updated", userId);
    }

    public UserDto getUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userMapper.toUserDto(user);
    }

    public UserExtendedDto getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId()).orElseThrow(UserNotFoundException::new);

        return userMapper.toUserExtendedDto(user);
    }
}
