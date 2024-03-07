package ru.seals.delivery.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class MessageTypeNotFoundException extends RuntimeException {
    public MessageTypeNotFoundException(String message) {
        super(message);
    }

    public MessageTypeNotFoundException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<MessageTypeNotFoundException> messageTypeNotFoundException(String message, Object... args) {
        return () -> new MessageTypeNotFoundException(message, args);
    }

    public static Supplier<MessageTypeNotFoundException> messageTypeNotFoundException(String message) {
        return () -> new MessageTypeNotFoundException(message);
    }
}
