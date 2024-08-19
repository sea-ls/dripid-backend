package ru.seals.delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class MoneyDTO {
    @JsonProperty("amount")
    BigDecimal amount;

    @JsonProperty("currency")
    String currency;
}
