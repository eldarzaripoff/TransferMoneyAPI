package ru.netology.TransferMoneyAPI.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class ConfirmOperationRequest {
    @NotBlank(message = "ID операции обязателен")
    String operationId;
    @NotBlank(message = "Верификационный код обязателен")
    String code;

    public ConfirmOperationRequest(@JsonProperty("operationId") String operationId, @JsonProperty("code") String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
