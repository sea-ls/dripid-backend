package ru.seals.delivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import ru.seals.delivery.model.enums.TransactionType;

@Entity
@Table(name = "balance_history")
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

    @Column(columnDefinition = "money")
    private Money oldBalance;
    @Column(columnDefinition = "money")
    private Money newBalance;
    private String cheque;
}
