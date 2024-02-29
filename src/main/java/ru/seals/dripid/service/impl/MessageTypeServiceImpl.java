package ru.seals.dripid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.seals.dripid.model.MessageType;
import ru.seals.dripid.repository.MessageTypeRepository;
import ru.seals.dripid.service.MessageTypeService;

import java.util.List;

import static ru.seals.dripid.exception.MessageTypeNotFoundException.messageTypeNotFoundException;

@Service
@RequiredArgsConstructor
public class MessageTypeServiceImpl implements MessageTypeService {
    private final MessageTypeRepository repository;

    @Override
    public MessageType getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public MessageType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(messageTypeNotFoundException("DefaultMessage {0} not found!", id));
    }

    @Override
    public List<MessageType> getAllMessageTypes() {
        return repository.findAll();
    }

    @Override
    public void save(MessageType messageType) {
        repository.save(messageType);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
