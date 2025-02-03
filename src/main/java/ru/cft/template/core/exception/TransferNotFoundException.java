package ru.cft.template.core.exception;

public class TransferNotFoundException extends RuntimeException {
  public TransferNotFoundException() {
    super(ExceptionMessage.TRANSFER_NOT_FOUND_MESSAGE);
  }
}
