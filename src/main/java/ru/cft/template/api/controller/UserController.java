package ru.cft.template.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.model.user.UserDto;
import ru.cft.template.api.model.user.UserUpdateRequest;
import ru.cft.template.core.service.UserService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable UUID userId) {
        return service.getUser(userId);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        service.updateUser(userId, userUpdateRequest);
        return ResponseEntity.ok("Пользователь обновлен");
    }
}
