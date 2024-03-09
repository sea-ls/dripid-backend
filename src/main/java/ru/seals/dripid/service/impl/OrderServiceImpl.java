package ru.seals.dripid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.seals.dripid.model.Order;
import ru.seals.dripid.repository.OrderRepository;
import ru.seals.dripid.service.OrderService;

import static ru.seals.dripid.exception.OrderNotFoundException.orderNotFoundException;

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
        return orderRepository.getOrderByTrackNumberExternal(trackNumber).getDeliveryHistory();
    }
}