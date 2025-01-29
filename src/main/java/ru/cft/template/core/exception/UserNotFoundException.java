package ru.cft.template.core.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Пользователь c указанным Id не найден");
    }
}
