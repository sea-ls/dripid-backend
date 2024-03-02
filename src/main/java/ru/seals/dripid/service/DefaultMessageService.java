package ru.seals.dripid.service;

import ru.seals.dripid.model.DefaultMessage;
import ru.seals.dripid.model.MessageType;

import java.util.List;

public interface DefaultMessageService {
    DefaultMessage getById(Long id);
    List<DefaultMessage> getAllByType(MessageType type);
    void save(DefaultMessage defaultMessage);
    void deleteById(Long id);
}
