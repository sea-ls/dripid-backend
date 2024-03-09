package ru.seals.dripid.service;

import ru.seals.dripid.model.Order;

public interface OrderService {
    Order getOrderById(Long id);
    void saveOrder(Order order);
    void deleteOrderById(Long id);
    String getDeliveryHistory(String trackNumber);
}
