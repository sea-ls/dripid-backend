package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.seals.delivery.model.delivery.Order;
import ru.seals.delivery.model.delivery.enums.OrderStatus;
import ru.seals.delivery.repository.OrderRepository;
import ru.seals.delivery.service.OrderService;

import java.util.List;
import java.util.Set;

import static ru.seals.delivery.exception.OrderNotFoundException.orderNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    @Override
    public Slice<Order> getAllByStatuses(Set<OrderStatus> statuses,
                                         Pageable pageable) {
        return orderRepository.findAllByStatuses(statuses, pageable);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(orderNotFoundException("Order {0} not found!", id));
    }

    public void saveOrder(Order order) {
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public String getDeliveryHistory(String trackNumber) {
        return orderRepository.findByTrackNumberExternal(trackNumber)
                .orElseThrow(orderNotFoundException("Order with track number = {0} not found!", trackNumber))
                .getDeliveryHistory();
    }

    @Override
    public Order getOrderByTrackIntervalNumber(String trackNumber) {
        return orderRepository.findByTrackNumberInternal(trackNumber)
                .orElseThrow(orderNotFoundException("Order with track number = {0} not found!", trackNumber));
    }

    @Override
    public List<Order> getAllOrderWithMessages() {
        return orderRepository.findAllWithMessage();
    }

    @Override
    public Page<Order> getUserOrders(Pageable pageable, Long id) {
        return orderRepository.findAllByPersonId(pageable, id);
    }
}
