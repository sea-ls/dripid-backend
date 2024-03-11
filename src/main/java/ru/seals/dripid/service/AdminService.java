package ru.seals.dripid.service;


import ru.seals.dripid.model.DefaultMessage;
import ru.seals.dripid.model.MessageType;
import ru.seals.dripid.model.Order;


import java.util.HashMap;
import java.util.List;

public interface AdminService {
    List<DefaultMessage> getAllDefaultMessagesByType(MessageType type);
    DefaultMessage getDefaultMessagesById(Long id);
    void deleteDefaultMessageById(Long id);
    //void saveDefaultMessage(DefaultMessage defaultMessage, String type);
    void saveDefaultMessage(DefaultMessage defaultMessage);

    List<MessageType> getAllMessageTypes();
    void deleteMessageTypeById(Long id);
    void saveMessageType(MessageType messageType);

    Order getOrderById(Long id);
    void saveOrder(Order order);
    void deleteOrderById(Long id);
    void updateDeliveryHistory(Long id, HashMap<String, String> newStatus);
}
