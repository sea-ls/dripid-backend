package ru.seals.delivery.service;

import ru.seals.delivery.model.chat.MessageType;

import java.util.List;

public interface MessageTypeService {
    MessageType getByName(String name);
    MessageType getById(Long id);
    List<MessageType> getAllMessageTypes();
    void save(MessageType messageType);
    void deleteById(Long id);
}