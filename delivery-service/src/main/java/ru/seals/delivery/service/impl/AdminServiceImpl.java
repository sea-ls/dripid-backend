package ru.seals.delivery.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.seals.delivery.model.delivery.DefaultMessage;
import ru.seals.delivery.model.delivery.Order;
import ru.seals.delivery.model.delivery.Product;
import ru.seals.delivery.model.delivery.chat.MessageType;
import ru.seals.delivery.model.delivery.enums.OrderStatus;
import ru.seals.delivery.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private final ProductService productService;
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updOrderStatusesByUpdMap(Map<OrderStatus, Object[]> map) { // check map def in AdminCronService
        Set<OrderStatus> statuses = map.keySet();
        Slice<Order> orders = orderService.getAllByStatuses(statuses, BATCH);
        orders.getContent().forEach(order -> updOrderStatusAndSendEmail(map, order));

        while (orders.hasNext()) {
            orders = orderService.getAllByStatuses(statuses, orders.nextPageable());
            orders.getContent().forEach(order -> updOrderStatusAndSendEmail(map, order));
        }
    }
    private void updOrderStatusAndSendEmail(Map<OrderStatus, Object[]> map,
                                            Order order) {
        OrderStatus oldStatus = order.getOrderStatus();
        OrderStatus newStatus = (OrderStatus) map.get(oldStatus)[0];
        LocalDateTime updTime = order.getLastModifiedDate()
                .plusMinutes((int) map.get(oldStatus)[1]);

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
    public void saveProduct(Product product, Long orderId) {
        product.setOrder(orderService.getOrderById(orderId));
        productService.save(product);
        log.info(String.format(SAVE_LOG, product.toString()));
    }

    @Override
    public void deleteProductById(Long id) {
        productService.deleteById(id);
        log.info(String.format(DELETE_LOG, "product", id));
    }

    @Override
    public void updateOrder(Order order) {
        orderService.saveOrder(order);
    }
}
