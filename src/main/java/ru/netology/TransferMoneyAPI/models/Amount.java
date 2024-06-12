package ru.netology.TransferMoneyAPI.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Amount {
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
