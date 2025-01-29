package ru.cft.template.core.exception;

public class CredentialAlreadyUsedException extends RuntimeException {
    public CredentialAlreadyUsedException(String message) {
        super(message);
    }
}
