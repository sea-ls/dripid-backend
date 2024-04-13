package ru.seals.delivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import ru.seals.delivery.model.enums.TransactionType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BalanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    private Person user;

    @ManyToOne
    private Person admin;
    private Money oldBalance;
    private Money newBalance;
    private String cheque;
    public BalanceHistory(TransactionType transactionType, Person user, Person admin, Money oldBalance, Money newBalance, String cheque) {
        this.transactionType = transactionType;
        this.user = user;
        this.admin = admin;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
        this.cheque = cheque;
    }
}
