package ru.netology.TransferMoneyAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ConfirmationDTO {
    @NotBlank(message = "ID операции обязателен")
    @Pattern(regexp = "^\\d{3}$", message = "oprationID должен содержать только цифры")
    private String operationId;
    @NotBlank(message = "Верификационный код обязателен")
    @Pattern(regexp = "^\\d{3}$", message = "Code должен содержать только цифры")
    private String code;

    public ConfirmationDTO(@JsonProperty("operationId") String operationId, @JsonProperty("code") String code) {
        this.operationId = operationId;
        this.code = code;
    }

    @Override
    public String toString() {
        return "ConfirmationDTO{" +
                "operationId='" + operationId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
