package ru.seals.delivery.hibernate.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(Money money) {
        return money.getNumber().numberValue(BigDecimal.class);
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal bigDecimal) {
        return Money.of(bigDecimal, "RUB");
    }
}
