package ru.seals.delivery.model.delivery;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import ru.seals.delivery.model.common.BaseEntity;
import ru.seals.delivery.model.delivery.enums.TransactionType;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BalanceHistory extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    private Person user;

    @ManyToOne
    private Person admin;
    private Money oldBalance;
    private Money newBalance;
    private String cheque;
}
