package ru.seals.dripid.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import ru.seals.dripid.model.DefaultMessage;
import ru.seals.dripid.model.MessageType;
import ru.seals.dripid.service.AdminService;
import ru.seals.dripid.service.DefaultMessageService;
import ru.seals.dripid.service.MessageTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final DefaultMessageService defaultMessageService;
    private final MessageTypeService messageTypeService;

    @Override
    public List<DefaultMessage> getAllDefaultMessagesByType(String type) {
        return defaultMessageService.getAllByType(type);
    }

    @Override
    public DefaultMessage getDefaultMessagesById(Long id) {
        return defaultMessageService.getById(id);
    }

    @Override
    public void deleteDefaultMessageById(Long id) {
        defaultMessageService.deleteById(id);
    }

    @Override
    public void saveDefaultMessage(DefaultMessage defaultMessage, String type) {
        defaultMessage.setMessageType(messageTypeService.getByName(type));
        defaultMessageService.save(defaultMessage);
    }

    @Override
    public List<MessageType> getAllMessageTypes() {
        return messageTypeService.getAllMessageTypes();
    }

    @Override
    public void deleteMessageTypeById(Long id) {
        messageTypeService.deleteById(id);
    }

    @Override
    public void saveMessageType(MessageType messageType) {
        messageTypeService.save(messageType);
    }
}
