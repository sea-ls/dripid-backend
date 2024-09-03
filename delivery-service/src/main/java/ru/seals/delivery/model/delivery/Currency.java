package ru.seals.delivery.model.delivery;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.seals.delivery.model.common.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currency")
public class Currency extends BaseEntity {
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
