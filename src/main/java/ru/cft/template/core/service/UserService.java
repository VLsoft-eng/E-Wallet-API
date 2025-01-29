package ru.cft.template.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.cft.template.api.model.user.UserCreateDto;
import ru.cft.template.api.model.user.UserDto;
import ru.cft.template.api.model.user.UserExtendedDto;
import ru.cft.template.api.model.user.UserUpdateRequest;
import ru.cft.template.core.entity.User;
import ru.cft.template.core.exception.CredentialAlreadyUsedException;
import ru.cft.template.core.exception.DoesntHaveRightsException;
import ru.cft.template.core.exception.ExceptionMessages;
import ru.cft.template.core.exception.UserNotFoundException;
import ru.cft.template.core.mapper.UserMapper;
import ru.cft.template.core.repository.UserRepository;
import ru.cft.template.core.security.userDetails.CustomUserDetails;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void createUser(UserCreateDto userCreateDto) {
        if (userRepository.existsByPhone(userCreateDto.phoneNumber())) {
            throw new CredentialAlreadyUsedException(ExceptionMessages.PHONE_ALREADY_USED_MESSAGE);
        }

        if (userRepository.existsByEmail(userCreateDto.email())) {
            throw new CredentialAlreadyUsedException(ExceptionMessages.EMAIL_ALREADY_USED_MESSAGE);
        }

        User user = userMapper.toUser(userCreateDto);
        userRepository.save(user);
    }

    public void updateUser(UUID userId, UserUpdateRequest userUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (!userDetails.getId().equals(userId)) {
            throw new DoesntHaveRightsException(ExceptionMessages.EDIT_OTHER_USER_NOT_ALLOWED_MESSAGE);
        }

        user.setFirstName(userUpdateRequest.firstName() != null ? userUpdateRequest.firstName() : user.getFirstName());
        user.setLastName(userUpdateRequest.lastName() != null ? userUpdateRequest.lastName() : user.getLastName());
        user.setMiddleName(userUpdateRequest.middleName() != null ? userUpdateRequest.middleName() : user.getMiddleName());
        user.setBirthdate(userUpdateRequest.birthdate() != null ? userUpdateRequest.birthdate() : user.getBirthdate());

        userRepository.save(user);
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
