package ru.seals.delivery.service;

import ru.seals.delivery.model.delivery.DefaultMessage;
import ru.seals.delivery.model.delivery.Order;
import ru.seals.delivery.model.delivery.Product;
import ru.seals.delivery.model.chat.MessageType;
import ru.seals.delivery.model.delivery.enums.OrderStatus;

import java.util.List;
import java.util.Map;

public interface AdminService {
    void updOrderStatusesByUpdMap(Map<OrderStatus, Object[]> map);
    List<DefaultMessage> getAllDefaultMessagesByType(MessageType type);
    DefaultMessage getDefaultMessagesById(Long id);
    void deleteDefaultMessageById(Long id);
    void saveDefaultMessage(DefaultMessage defaultMessage);

    List<MessageType> getAllMessageTypes();
    void deleteMessageTypeById(Long id);
    void saveMessageType(MessageType messageType);

    void deleteOrderById(Long id);
    void updateOrder(Order order);

    void updateDeliveryHistory(Long id, Map<String, String> newStatus);

    void saveProduct(Product product, Long orderId);
    void deleteProductById(Long id);
}
