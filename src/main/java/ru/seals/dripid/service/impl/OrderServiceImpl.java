package ru.seals.dripid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.seals.dripid.exception.OrderNotFoundException;
import ru.seals.dripid.model.Order;
import ru.seals.dripid.repository.OrderRepository;
import ru.seals.dripid.service.OrderService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new OrderNotFoundException("Order with " + id + " not found");
        }
    }
}
