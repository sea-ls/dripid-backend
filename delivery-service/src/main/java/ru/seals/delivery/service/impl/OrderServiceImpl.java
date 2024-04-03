package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.repository.OrderRepository;
import ru.seals.delivery.service.OrderService;

import java.util.List;

import static ru.seals.delivery.exception.OrderNotFoundException.orderNotFoundException;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(orderNotFoundException("Order {0} not found!", id));
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public String getDeliveryHistory(String trackNumber) {
        return orderRepository.findByTrackNumberExternal(trackNumber).getDeliveryHistory();
    }

    @Override
    public Order getOrderByTrackIntervalNumber(String trackNumber) {
        return orderRepository.findByTrackNumberInternal(trackNumber);
    }

    @Override
    public List<Order> getAllOrderWithMessages() {
        return orderRepository.findAllWithMessage();
    }
}
