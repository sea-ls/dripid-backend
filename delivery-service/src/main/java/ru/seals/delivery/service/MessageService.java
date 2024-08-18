package ru.seals.delivery.service;





import ru.seals.delivery.model.chat.Message;

import java.util.List;

public interface MessageService {
    Message save(Message chatMessage);

    List<Message> findChatMessages(Long orderId);

}
