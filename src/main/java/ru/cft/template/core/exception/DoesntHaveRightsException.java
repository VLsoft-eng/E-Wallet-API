package ru.cft.template.core.exception;

public class DoesntHaveRightsException extends RuntimeException {
    public DoesntHaveRightsException(String message) {
        super(message);
    }
}
