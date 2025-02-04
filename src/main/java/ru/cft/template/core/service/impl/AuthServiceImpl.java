package ru.cft.template.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.template.api.model.auth.LoginRequest;
import ru.cft.template.api.model.auth.LoginResponse;
import ru.cft.template.api.model.auth.RegistrationRequest;
import ru.cft.template.api.model.auth.RegistrationResponse;
import ru.cft.template.core.dto.UserCreateDto;
import ru.cft.template.core.mapper.RegistrationMapper;
import ru.cft.template.core.security.userDetails.CustomUserDetails;
import ru.cft.template.core.security.userDetails.CustomUserDetailsService;
import ru.cft.template.core.service.AuthService;
import ru.cft.template.core.service.JwtService;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationMapper registrationMapper;

    public RegistrationResponse signUp(RegistrationRequest registrationRequest) {
        String hashedPassword = passwordEncoder.encode(registrationRequest.password());
        UserCreateDto userCreateDto = registrationMapper.toUserCreateDto(registrationRequest, hashedPassword);

        UUID userId = userService.createUser(userCreateDto);

        return new RegistrationResponse(userId);
    }

    public LoginResponse signIn(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));

        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(request.email());

        String jwt = jwtService.generateToken(userDetails);
        return new LoginResponse(jwt);
    }
}
