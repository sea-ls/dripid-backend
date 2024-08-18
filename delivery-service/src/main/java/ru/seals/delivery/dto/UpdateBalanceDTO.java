package ru.seals.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.seals.delivery.model.delivery.enums.TransactionType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBalanceDTO {
    private TransactionType type;
    private BigDecimal amount;
    private String cheque;
}
