package ru.cft.template.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.template.api.model.auth.LoginRequest;
import ru.cft.template.api.model.auth.LoginResponse;
import ru.cft.template.api.model.auth.UserCreateRequest;
import ru.cft.template.core.service.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> singUp(@RequestBody @Valid UserCreateRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok("Регистрация прошла успешно.");
    }

    @PostMapping("/sign-in")
    public LoginResponse signIn(@RequestBody @Valid LoginRequest request) {
        return authService.signIn(request);
    }
}
