package ru.cft.template.core.exception;

public class SelfTransferException extends RuntimeException {
    public SelfTransferException() {
        super(ExceptionMessage.SELF_TRANSFER_MESSAGE);
    }
}
