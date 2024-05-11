package ru.seals.delivery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.model.enums.OrderStatus;

import java.util.List;
import java.util.Set;


public interface OrderService {
    Slice<Order> getAllByStatuses(Set<OrderStatus> statuses,
                                  Pageable pageable);
    Order getOrderById(Long id);

    void saveOrder(Order order);

    void deleteOrderById(Long id);

    String getDeliveryHistory(String trackNumber);

    Order getOrderByTrackIntervalNumber(String trackNumber);

    List<Order> getAllOrderWithMessages();

    Page<Order> getUserOrders(Pageable pageable, Long id);
}
