package ru.seals.delivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "updated_timestamp")
    private LocalDateTime updatedTimestamp;
    @Column(name = "currency_name")
    private String currencyName;
    @Column(name = "quickly_purchase")
    private BigDecimal quicklyPurchase;
    @Column(name = "non_quickly_purchase")
    private BigDecimal nonQuicklyPurchase;
    @Column(name = "crypto")
    private BigDecimal crypto;
}
