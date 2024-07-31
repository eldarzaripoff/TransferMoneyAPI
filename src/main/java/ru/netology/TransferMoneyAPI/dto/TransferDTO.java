package ru.netology.TransferMoneyAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import ru.netology.TransferMoneyAPI.models.Amount;

@Getter
@Setter
public class TransferDTO {
    @NotBlank(message = "Номер карты обязателен")
    @Size(min = 16, max = 16, message = "Номер карты должен содержать минимум 16 символов")
    @Pattern(regexp = "^2\\d{15}$", message = "Номер карты должен начинаться с '2' и содержать только цифры")
    private String cardFromNumber;
    @NotBlank(message = "Срок действия карты обязателен")
    @Size(min = 4, max = 4, message = "Срок действия карты должен содержать минимум 4 символа")
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\d{2}$", message = "Срок действия карты должен быть в формате MMYY")
    private String cardFromValidTill;
    @NotBlank(message = "CVC обязателен")
    @Size(min = 3, max = 3, message = "CVC должен содержать минимум 3 символа")
    @Pattern(regexp = "^\\d{3}$", message = "CVC должен содержать только цифры")
    private String cardFromCVV;
    @NotBlank(message = "Номер карты получателя обязателен")
    @Size(min = 16, max = 16, message = "Номер карты получателя должен содержать минимум 16 символов")
    @Pattern(regexp = "^2\\d{15}$", message = "Номер карты должен начинаться с '2' и содержать только цифры")
    private String cardToNumber;
    @NotNull(message = "Сумма перевода обязательна")
    private Amount amount;

    public TransferDTO(@JsonProperty("cardFromNumber") String cardFromNumber, @JsonProperty("cardFromValidTill") String cardFromValidTill,
                       @JsonProperty("cardFromCVV") String cardFromCVV, @JsonProperty("cardToNumber") String cardToNumber, @JsonProperty("amount") Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
                "cardFromNumber='" + cardFromNumber + '\'' +
                ", cardFromValidTill='" + cardFromValidTill + '\'' +
                ", cardFromCVV='" + cardFromCVV + '\'' +
                ", cardToNumber='" + cardToNumber + '\'' +
                ", amount=" + amount +
                '}';
    }
}
