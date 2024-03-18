package ru.seals.delivery.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class DefaultMessageNotFoundException extends RuntimeException {
    public DefaultMessageNotFoundException(String message) {
        super(message);
    }

    public DefaultMessageNotFoundException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<DefaultMessageNotFoundException> defaultMessageNotFoundException(String message, Object... args) {
        return () -> new DefaultMessageNotFoundException(message, args);
    }

    public static Supplier<DefaultMessageNotFoundException> defaultMessageNotFoundException(String message) {
        return () -> new DefaultMessageNotFoundException(message);
    }
}
