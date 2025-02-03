package ru.cft.template.core.exception;

public class BalanceLessThanTransferException extends RuntimeException {
    public BalanceLessThanTransferException() {
        super(ExceptionMessage.BALANCE_LESS_THAN_TRANSFER_MESSAGE);
    }
}
