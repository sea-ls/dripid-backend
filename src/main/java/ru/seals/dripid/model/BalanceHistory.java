package ru.seals.dripid.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import ru.seals.dripid.model.enums.TransactionType;

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
    private TransactionType type;

    @ManyToOne
    private User user;

    @ManyToOne
    private User admin;

    private Money oldBalance;
    private Money newBalance;
    private String check;
}
