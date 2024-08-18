package ru.seals.delivery.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.seals.delivery.model.delivery.chat.Message;
import ru.seals.delivery.repository.MessageRepository;
import ru.seals.delivery.service.MessageService;
import ru.seals.delivery.service.OrderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final OrderService orderService;
    @Override
    public Message save(Message message) {
        //message.setStatus(MessageStatus.DELIVERED);
        messageRepository.save(message);
        return message;
    }


    @Override
    public List<Message> findChatMessages(Long orderId) {
        return messageRepository.findAllByOrderId(orderId);
    }
}
