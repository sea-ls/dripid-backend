package ru.seals.delivery.service;

import ru.seals.delivery.dto.OrderSaveDTO;
import ru.seals.delivery.model.DefaultMessage;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.model.chat.MessageType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AdminService {
    List<DefaultMessage> getAllDefaultMessagesByType(MessageType type);
    DefaultMessage getDefaultMessagesById(Long id);
    void deleteDefaultMessageById(Long id);
    void saveDefaultMessage(DefaultMessage defaultMessage);

    List<MessageType> getAllMessageTypes();
    void deleteMessageTypeById(Long id);
    void saveMessageType(MessageType messageType);

    void saveOrder(OrderSaveDTO orderSaveDTO);
    void deleteOrderById(Long id);

    void updateDeliveryHistory(Long id, Map<String, String> newStatus);
    Order getOrderByTrackIntervalNumber(String trackNumber);

    void updateOrder(Order order);
}
