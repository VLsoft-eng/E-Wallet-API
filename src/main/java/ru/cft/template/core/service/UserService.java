package ru.cft.template.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cft.template.core.dto.user.UserCreateDto;
import ru.cft.template.core.entity.User;
import ru.cft.template.core.exception.EmailAlreadyUsedException;
import ru.cft.template.core.exception.PhoneAlreadyUsedException;
import ru.cft.template.core.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserCreateDto userCreateDto) {
        if (userRepository.existsByPhone(userCreateDto.phoneNumber())) {
            throw new PhoneAlreadyUsedException();
        }

        if (userRepository.existsByEmail(userCreateDto.email())) {
            throw new EmailAlreadyUsedException();
        }

        User user = User.builder()
                .firstName(userCreateDto.firstName())
                .lastName(userCreateDto.lastName())
                .middleName(userCreateDto.middleName())
                .phone(userCreateDto.phoneNumber())
                .email(userCreateDto.email())
                .birthdate(userCreateDto.birthDate())
                .hashedPassword(userCreateDto.hashedPassword())
                .build();

        userRepository.save(user);
    }
}
