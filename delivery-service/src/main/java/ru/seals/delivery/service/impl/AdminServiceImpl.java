package ru.seals.delivery.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AdminServiceImpl implements AdminService {
    private static final String SAVE_LOG = "Сохранение записи в таблице '%s' с ID = %d выполнено успешно.";
    private static final String DELETE_LOG = "Удаление записи в таблице '%s' с ID = %d выполнено успешно.";
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
        log.info(String.format(DELETE_LOG, "default_message", id));
    }

    @Override
    public void saveDefaultMessage(DefaultMessage defaultMessage) {
        defaultMessageService.save(defaultMessage);
        log.info(String.format(SAVE_LOG, "default_message", defaultMessage.getId()));
    }


    @Override
    public List<MessageType> getAllMessageTypes() {
        return messageTypeService.getAllMessageTypes();
    }

    @Override
    public void deleteMessageTypeById(Long id) {
        messageTypeService.deleteById(id);
        log.info(String.format(DELETE_LOG, "message_type", id));
    }

    @Override
    public void saveMessageType(MessageType messageType) {
        messageTypeService.save(messageType);
        log.info(String.format(SAVE_LOG, "message_type", messageType.getId()));
    }

    @Override
    public Order getOrderById(Long id) {
        return orderService.getOrderById(id);
    }

    @Override
    public void saveOrder(Order order) {
        orderService.saveOrder(order);
        log.info(String.format(SAVE_LOG, "order", order.getId()));
    }

    @Override
    public void deleteOrderById(Long id) {
        orderService.deleteOrderById(id);
        log.info(String.format(DELETE_LOG, "order", id));
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
            log.error(String.format("Обновление последнего статуса доставки заказа с ID = %d не выполнено.", id));
            throw new RuntimeException(e);
        }

        orderService.saveOrder(order);
        log.info(String.format("Обновление последнего статуса доставки заказа с ID = %d выполнено успешно.", id));
    }

    @Override
    public Order getOrderByTrackIntervalNumber(String trackNumber) {
        return orderService.getOrderByTrackIntervalNumber(trackNumber);
    }
}
