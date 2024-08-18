package ru.seals.delivery.service;

import ru.seals.delivery.model.delivery.DefaultMessage;
import ru.seals.delivery.model.delivery.chat.MessageType;

import java.util.List;

public interface DefaultMessageService {
    DefaultMessage getById(Long id);
    List<DefaultMessage> getAllByType(MessageType type);
    void save(DefaultMessage defaultMessage);
    void deleteById(Long id);
}
