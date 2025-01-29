package ru.cft.template.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.template.core.dto.auth.LoginRequest;
import ru.cft.template.core.dto.auth.TokenDto;
import ru.cft.template.core.dto.auth.UserCreateRequest;
import ru.cft.template.core.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> singUp(@RequestBody @Valid UserCreateRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok("Регистрация прошла успешно.");
    }

    @PostMapping("/sign-in")
    public TokenDto signIn(@RequestBody @Valid LoginRequest request) {
        return authService.signIn(request);
    }
}
