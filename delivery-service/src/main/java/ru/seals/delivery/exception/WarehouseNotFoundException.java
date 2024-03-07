package ru.seals.delivery.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException(String message) {
        super(message);
    }

    public WarehouseNotFoundException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<WarehouseNotFoundException> warehouseNotFoundException(String message, Object... args) {
        return () -> new WarehouseNotFoundException(message, args);
    }

    public static Supplier<WarehouseNotFoundException> warehouseNotFoundException(String message) {
        return () -> new WarehouseNotFoundException(message);
    }
}