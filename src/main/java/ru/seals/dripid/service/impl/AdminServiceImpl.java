package ru.seals.dripid.service.impl;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import ru.seals.dripid.model.Order;
import ru.seals.dripid.service.*;
import ru.seals.dripid.model.DefaultMessage;
import ru.seals.dripid.model.MessageType;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private static final String DELIVERY_HISTORY_JSON = "%s, {\"time\": \"%s\", \"status\": \"%s\"}]";
    private static final String DELIVERY_START_HISTORY_JSON = "[{\"time\": \"%s\", \"status\": \"%s\"}]";
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
    public void updateDeliveryHistory(Long id, String status) {
        Order order = orderService.getOrderById(id);
        String oldStatus = order.getDeliveryHistory();
        String newStatus = Objects.isNull(oldStatus) ?
                String.format(DELIVERY_START_HISTORY_JSON, LocalDate.now(), status) :
                String.format(DELIVERY_HISTORY_JSON, oldStatus.substring(0, oldStatus.length() - 1), LocalDate.now(), status);

        order.setDeliveryHistory(newStatus);
        orderService.saveOrder(order);
    }
}
