package ru.netology.TransferMoneyAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.netology.TransferMoneyAPI.models.Amount;

@Getter
@Setter
public class TransferDTO {
    @NotBlank(message = "Номер карты обязателен")
    @Size(min = 16, message = "Номер карты должен содержать минимум 16 символов")
    private String cardFromNumber;
    @NotBlank(message = "Срок действия карты обязателен")
    @Size(min = 4, message = "Срок действия карты должен содержать минимум 4 символа")
    private String cardFromValidTill;
    @NotBlank(message = "CVC обязателен")
    @Size(min = 3, message = "CVC должен содержать минимум 3 символа")
    private String cardFromCVV;
    @NotBlank(message = "Номер карты получателя обязателен")
    @Size(min = 16, message = "Номер карты получателя должен содержать минимум 16 символов")
    private String cardToNumber;
    @NotNull(message = "Сумма перевода обязательна")
    @Min(value = 1, message = "Сумма перевода не может быть равна или меньше 0")
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
