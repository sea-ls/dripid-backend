package ru.seals.delivery.service;

import ru.seals.delivery.model.DefaultMessage;
import ru.seals.delivery.model.chat.MessageType;

import java.util.List;

public interface DefaultMessageService {
    DefaultMessage getById(Long id);
    List<DefaultMessage> getAllByType(MessageType type);
    void save(DefaultMessage defaultMessage);
    void deleteById(Long id);
}
