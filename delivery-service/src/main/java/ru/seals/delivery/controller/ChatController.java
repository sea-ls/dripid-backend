package ru.seals.delivery.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.model.chat.Message;
import ru.seals.delivery.service.MessageService;
import ru.seals.delivery.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final MessageService messageService;
    private final OrderService orderService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(
            @Payload Message chatMessage
    ) {
        messageService.save(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(
            @Payload Message chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @GetMapping("/messages/{orderId}")
    public ResponseEntity<List<Message>> findMessages(@PathVariable Long orderId) {
        return ResponseEntity
                .ok(messageService.findChatMessages(orderId));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> findOrderWithMessage() {
        return ResponseEntity.ok(orderService.getAllOrderWithMessages());
    }
}
