package ru.netology.TransferMoneyAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ConfirmationDTO {
    @NotBlank(message = "ID операции обязателен")
    private String operationId;
    @NotBlank(message = "Верификационный код обязателен")
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
