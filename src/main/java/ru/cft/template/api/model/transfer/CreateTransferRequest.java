package ru.cft.template.api.model.transfer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateTransferRequest(
        @NotBlank(message = "Данные получателя не могут быть пустым.")
        String receiverCredential,
        @Min(value = 0, message = "Сумма перевода должна быть больше нуля")
        @NotBlank(message = "Сумма перевода обязательна.")
        Long amount
) {
}
