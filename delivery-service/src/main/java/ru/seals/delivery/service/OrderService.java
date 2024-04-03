package ru.seals.delivery.service;


import ru.seals.delivery.model.Order;

import java.util.List;


public interface OrderService {
    Order getOrderById(Long id);

    void saveOrder(Order order);

    void deleteOrderById(Long id);

    String getDeliveryHistory(String trackNumber);

    Order getOrderByTrackIntervalNumber(String trackNumber);

    List<Order> getAllOrderWithMessages();
}
