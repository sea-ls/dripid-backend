package ru.seals.delivery.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.seals.delivery.model.DefaultMessage;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.model.chat.MessageType;
import ru.seals.delivery.model.enums.OrderStatus;
import ru.seals.delivery.service.AdminService;
import ru.seals.delivery.service.DefaultMessageService;
import ru.seals.delivery.service.MessageTypeService;
import ru.seals.delivery.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    private static final String SAVE_LOG = "Сохранение объекта '%s' выполнено успешно.";
    private static final String DELETE_LOG = "Удаление записи в таблице '%s' с ID = %d выполнено успешно.";
    private static final PageRequest BATCH = PageRequest.of(0, 10);
    private final DefaultMessageService defaultMessageService;
    private final MessageTypeService messageTypeService;
    private final OrderService orderService;
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updOrdersStatus() {
        Slice<Order> orders = orderService.getAllByUpdStatus(BATCH);
        orders.getContent().forEach(this::updOrderStatusAndSendEmail);

        while (orders.hasNext()) {
            orders = orderService.getAllByUpdStatus(orders.nextPageable());
            orders.getContent().forEach(this::updOrderStatusAndSendEmail);
        }
    }
    private void updOrderStatusAndSendEmail(Order order) {
        //contains old status and new status + upd time
        //in min, can be in days (prod)
        Map<OrderStatus, Object[]> updStatusMap = Map.of(
                OrderStatus.TEST, new Object[]{OrderStatus.UPD_TEST, 2}
        );
        OrderStatus oldStatus = order.getOrderStatus();
        OrderStatus newStatus = (OrderStatus) updStatusMap.get(oldStatus)[0];
        LocalDateTime updTime = order.getLastUpdate()
                .plusMinutes((int) updStatusMap.get(oldStatus)[1]);

        if (LocalDateTime.now().isAfter(updTime)) {
            order.setOrderStatus(newStatus);
            updateDeliveryHistory(order.getId(), Map.of(
                    "time", "%s".formatted(LocalDateTime.now().toString()),
                    "status", "Статус заказа обновлен на %s".formatted(newStatus)));
            log.info("""
                    Status of order %d was updated (%s, %s)\s
                    """.formatted(order.getId(), oldStatus, newStatus));
            orderService.saveOrder(order);
        }
    }

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
        log.info(String.format(SAVE_LOG, defaultMessage.toString()));
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
        log.info(String.format(SAVE_LOG, messageType.toString()));
    }

    @Override
    public void saveOrder(Order order) {
        orderService.saveOrder(order);
        log.info(String.format(SAVE_LOG, order.getId()));
    }

    @Override
    public void deleteOrderById(Long id) {
        orderService.deleteOrderById(id);
        log.info(String.format(DELETE_LOG, "order", id));
    }

    @Override
    public void updateDeliveryHistory(Long id, Map<String, String> newStatus) {
        Order order = orderService.getOrderById(id);
        String oldStatus = order.getDeliveryHistory();

        try {
            ObjectMapper om = new ObjectMapper();
            ArrayList<Map<String, String>> statuses = om.readValue(oldStatus, ArrayList.class);
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
