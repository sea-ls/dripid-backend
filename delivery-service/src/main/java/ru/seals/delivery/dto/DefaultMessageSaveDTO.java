package ru.seals.delivery.dto;

import lombok.Data;
import ru.seals.delivery.model.delivery.chat.MessageType;

@Data
public class DefaultMessageSaveDTO {
    private MessageType messageType;
    private String message;
}
