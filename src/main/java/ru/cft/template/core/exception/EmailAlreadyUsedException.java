package ru.cft.template.core.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException() {
        super("Почта уже занята.");
    }
}
