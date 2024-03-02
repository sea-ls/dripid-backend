package ru.seals.dripid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.seals.dripid.model.DefaultMessage;
import ru.seals.dripid.model.MessageType;
import ru.seals.dripid.repository.DefaultMessageRepository;
import ru.seals.dripid.service.DefaultMessageService;

import java.util.List;

import static ru.seals.dripid.exception.DefaultMessageNotFoundException.defaultMessageNotFoundException;

@Service
@RequiredArgsConstructor
public class DefaultMessageServiceImpl implements DefaultMessageService {
    private final DefaultMessageRepository repository;

    @Override
    public DefaultMessage getById(Long id) {
        return repository.findById(id)
                .orElseThrow(defaultMessageNotFoundException("DefaultMessage {0} not found!", id));
    }

    @Override
    public List<DefaultMessage> getAllByType(MessageType type) {
        return repository.findAllByMessageType(type);
    }

    @Override
    public void save(DefaultMessage defaultMessage) {
        repository.save(defaultMessage);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
