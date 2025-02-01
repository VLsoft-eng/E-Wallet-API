package ru.cft.template.core.exception;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException() {
        super(ExceptionMessage.WALLET_NOT_FOUND_MESSAGE);
    }
}
