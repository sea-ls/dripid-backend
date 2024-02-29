package ru.seals.dripid.service;

import ru.seals.dripid.model.DefaultMessage;

import java.util.List;

public interface DefaultMessageService {
    DefaultMessage getById(Long id);
    List<DefaultMessage> getAllByType(String type);
    void save(DefaultMessage defaultMessage);
    void deleteById(Long id);
}
