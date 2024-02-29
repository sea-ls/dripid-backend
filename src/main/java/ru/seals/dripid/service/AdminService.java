package ru.seals.dripid.service;

import ru.seals.dripid.model.DefaultMessage;
import ru.seals.dripid.model.MessageType;

import java.util.List;

public interface AdminService {
    List<DefaultMessage> getAllDefaultMessagesByType(String type);
    DefaultMessage getDefaultMessagesById(Long id);
    void deleteDefaultMessageById(Long id);
    void saveDefaultMessage(DefaultMessage defaultMessage, String type);

    List<MessageType> getAllMessageTypes();
    void deleteMessageTypeById(Long id);
    void saveMessageType(MessageType messageType);
}
