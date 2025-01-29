package ru.cft.template.core.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super(ExceptionMessages.USER_NOT_FOUND_MESSAGE);
    }
}
