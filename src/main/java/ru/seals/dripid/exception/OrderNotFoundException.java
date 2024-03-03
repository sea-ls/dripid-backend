package ru.seals.dripid.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<OrderNotFoundException> orderNotFoundException(String message, Object... args) {
        return () -> new OrderNotFoundException(message, args);
    }
}
