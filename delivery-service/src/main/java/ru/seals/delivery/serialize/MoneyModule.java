package ru.seals.delivery.serialize;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.javamoney.moneta.Money;

public class MoneyModule extends SimpleModule {
    public MoneyModule() {
        addSerializer( Money.class, new MoneySerializer());
    }
}
