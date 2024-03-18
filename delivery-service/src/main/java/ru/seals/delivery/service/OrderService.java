package ru.seals.delivery.service;

import ru.seals.delivery.model.Order;

public interface OrderService {
    Order getOrderById(Long id);
    void saveOrder(Order order);
    void deleteOrderById(Long id);
    String getDeliveryHistory(String trackNumber);
}
