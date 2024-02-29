package ru.seals.dripid.service;

import ru.seals.dripid.model.MessageType;

import java.util.List;

public interface MessageTypeService {
    MessageType getByName(String name);
    MessageType getById(Long id);
    List<MessageType> getAllMessageTypes();
    void save(MessageType messageType);
    void deleteById(Long id);
}
