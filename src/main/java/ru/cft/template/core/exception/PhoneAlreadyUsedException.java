package ru.cft.template.core.exception;

public class PhoneAlreadyUsedException extends RuntimeException {
    public PhoneAlreadyUsedException() {
        super("Телефон уже используется");
    }
}
