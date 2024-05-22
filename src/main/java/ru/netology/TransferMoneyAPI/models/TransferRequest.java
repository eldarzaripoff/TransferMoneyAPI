package ru.netology.TransferMoneyAPI.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TransferRequest {
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

    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public void setCardFromNumber(String cardFromNumber) {
        this.cardFromNumber = cardFromNumber;
    }

    public String getCardFromValidTill() {
        return cardFromValidTill;
    }

    public void setCardFromValidTill(String cardFromValidTill) {
        this.cardFromValidTill = cardFromValidTill;
    }

    public String getCardFromCVV() {
        return cardFromCVV;
    }

    public void setCardFromCVV(String cardFromCVV) {
        this.cardFromCVV = cardFromCVV;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public TransferRequest(@JsonProperty("cardFromNumber") String cardFromNumber, @JsonProperty("cardFromValidTill") String cardFromValidTill,
                           @JsonProperty("cardFromCVV") String cardFromCVV, @JsonProperty("cardToNumber") String cardToNumber, @JsonProperty("amount") Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
    }

    public static class Amount {
        private Integer value;
        private String currency;

        public Amount(@JsonProperty("value") Integer value, @JsonProperty("currency") String currency) {
            this.value = value;
            this.currency = currency;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }
}
