package ru.seals.delivery.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.seals.delivery.model.delivery.Order;
import ru.seals.delivery.model.delivery.chat.Message;
import ru.seals.delivery.service.MessageService;
import ru.seals.delivery.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/delivery-service/chats")
public class ChatController {
    private final MessageService messageService;
    private final OrderService orderService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    @Operation(description = "Веб-сокет для отправки сообщения")
    public Message sendMessage(
            @Payload Message chatMessage
    ) {
        messageService.save(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    @Operation(description = "Веб-сокет для добавления юзера в чат")
    public Message addUser(
            @Payload Message chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @GetMapping("/messages/{orderId}")
    @Operation(description = "Получения всех сообщений по ID заказа")
    public ResponseEntity<List<Message>> findMessages(@PathVariable Long orderId) {
        return ResponseEntity
                .ok(messageService.findChatMessages(orderId));
    }

    @GetMapping("/orders")
    @Operation(description = "Получения всех заказов с сообщениями")
    public ResponseEntity<List<Order>> findOrderWithMessage() {
        return ResponseEntity.ok(orderService.getAllOrderWithMessages());
    }
}
