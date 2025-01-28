package ru.cft.template.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.template.core.dto.auth.LoginRequest;
import ru.cft.template.core.dto.auth.TokenResponse;
import ru.cft.template.core.dto.auth.UserCreateRequest;
import ru.cft.template.core.dto.user.UserCreateDto;
import ru.cft.template.core.security.userDetails.CustomUserDetails;
import ru.cft.template.core.security.userDetails.CustomUserDetailsService;

@Service
public class AuthService {
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserService userService, CustomUserDetailsService customUserDetailsService, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void signUp(UserCreateRequest userCreateRequest) {
        String hashedPassword = passwordEncoder.encode(userCreateRequest.password());
        UserCreateDto userCreateDto = new UserCreateDto(
                userCreateRequest.lastName(),
                userCreateRequest.firstName(),
                userCreateRequest.middleName(), userCreateRequest.phoneNumber(),
                userCreateRequest.email(),
                userCreateRequest.birthdate(),
                hashedPassword);

        userService.createUser(userCreateDto);
    }

    public TokenResponse signIn(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));

        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(request.email());

        String jwt = jwtService.generateToken(userDetails);
        return new TokenResponse(jwt);
    }
}
