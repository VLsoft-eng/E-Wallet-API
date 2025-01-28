package ru.cft.template.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.core.dto.auth.LoginRequest;
import ru.cft.template.core.dto.auth.TokenResponse;
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
        return ResponseEntity.ok("Sign up successful");
    }

    @PostMapping("/sign-in")
    public TokenResponse signIn(@RequestBody @Valid LoginRequest request) {
        System.out.println("UMER");
        return authService.signIn(request);
    }
}
