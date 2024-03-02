package ru.seals.dripid.service;

import ru.seals.dripid.model.Order;

public interface OrderService {
    Order getOrderById(Long id);
}
