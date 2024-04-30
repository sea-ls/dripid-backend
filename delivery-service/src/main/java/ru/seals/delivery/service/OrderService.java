package ru.seals.delivery.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.seals.delivery.dto.OrderPreviewDTO;
import ru.seals.delivery.model.Order;

import java.util.List;


public interface OrderService {
    Order getOrderById(Long id);

    void saveOrder(Order order);

    void deleteOrderById(Long id);

    String getDeliveryHistory(String trackNumber);

    Order getOrderByTrackIntervalNumber(String trackNumber);

    List<Order> getAllOrderWithMessages();

    Page<Order> getUserOrders(Pageable pageable, Long id);
}
