package ru.cft.template.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.core.dto.user.UserDto;
import ru.cft.template.core.dto.user.UserExtendedDto;
import ru.cft.template.core.dto.user.UserUpdateRequest;
import ru.cft.template.core.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/my-profile")
    public UserExtendedDto getUserProfile() {
        return userService.getUserProfile();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userId, userUpdateRequest);
        return ResponseEntity.ok("Пользователь обновлен");
    }
}
