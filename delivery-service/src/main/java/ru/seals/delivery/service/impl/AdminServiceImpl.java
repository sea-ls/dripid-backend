package ru.seals.delivery.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.seals.delivery.model.DefaultMessage;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.model.chat.MessageType;
import ru.seals.delivery.service.AdminService;
import ru.seals.delivery.service.DefaultMessageService;
import ru.seals.delivery.service.MessageTypeService;
import ru.seals.delivery.service.OrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final DefaultMessageService defaultMessageService;
    private final MessageTypeService messageTypeService;
    private final OrderService orderService;

    @Override
    public List<DefaultMessage> getAllDefaultMessagesByType(MessageType type) {
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

    /*@Override
    public void saveDefaultMessage(DefaultMessage defaultMessage, String type) {
        defaultMessage.setMessageType(messageTypeService.getByName(type));
        defaultMessageService.save(defaultMessage);
    }*/
    @Override
    public void saveDefaultMessage(DefaultMessage defaultMessage) {
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

    @Override
    public Order getOrderById(Long id) {
        return orderService.getOrderById(id);
    }

    @Override
    public void saveOrder(Order order) {
        orderService.saveOrder(order);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderService.deleteOrderById(id);
    }

    @Override
    public void updateDeliveryHistory(Long id, HashMap<String, String> newStatus) {
        Order order = orderService.getOrderById(id);
        String oldStatus = order.getDeliveryHistory();

        try {
            ObjectMapper om = new ObjectMapper();
            ArrayList<HashMap<String, String>> statuses = om.readValue(oldStatus, ArrayList.class);
            statuses.add(newStatus);
            order.setDeliveryHistory(om.writeValueAsString(statuses));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        orderService.saveOrder(order);
    }

    @Override
    public Order getOrderByTrackIntervalNumber(String trackNumber) {
        return orderService.getOrderByTrackIntervalNumber(trackNumber);
    }
}
