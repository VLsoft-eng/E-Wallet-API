package ru.cft.template.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.template.api.model.auth.LoginRequest;
import ru.cft.template.api.model.auth.LoginResponse;
import ru.cft.template.api.model.auth.UserCreateRequest;
import ru.cft.template.api.model.user.UserCreateDto;
import ru.cft.template.core.security.userDetails.CustomUserDetails;
import ru.cft.template.core.security.userDetails.CustomUserDetailsService;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

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
