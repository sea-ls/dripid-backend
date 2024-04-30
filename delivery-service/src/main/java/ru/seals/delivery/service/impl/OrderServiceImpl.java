package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.model.enums.OrderStatus;
import ru.seals.delivery.repository.OrderRepository;
import ru.seals.delivery.service.OrderService;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import static ru.seals.delivery.exception.OrderNotFoundException.orderNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PageRequest BATCH = PageRequest.of(0, 10);

    //@Scheduled(cron = "0 0 12,00 * * *") it can be im prod
    @Scheduled(fixedDelay = 3000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changeOrdersStatus() {
        Slice<Order> orders = orderRepository.findAllByUpdStatus(EnumSet.of(OrderStatus.TEST), BATCH);
        orders.getContent().forEach(this::updOrderStatusAndSendEmail);

        while (orders.hasNext()) {
            orders = orderRepository.findAllByUpdStatus(EnumSet.of(OrderStatus.TEST), BATCH);
            orders.getContent().forEach(this::updOrderStatusAndSendEmail);
        }
    }
    private void updOrderStatusAndSendEmail(Order order) {
        //contains old status and new status + upd time
        //in min, can be in days (prod)
        Map<OrderStatus, Object[]> updStatusMap = Map.of(
            OrderStatus.TEST, new Object[]{OrderStatus.UPD_TEST, 2}
        );
        OrderStatus oldStatus = order.getOrderStatus();
        OrderStatus newStatus = (OrderStatus) updStatusMap.get(oldStatus)[0];
        LocalDateTime updTime = order.getLastUpdate()
                .plusMinutes((int) updStatusMap.get(oldStatus)[1]);

        if (LocalDateTime.now().isAfter(updTime)) {
            order.setOrderStatus(newStatus);
            log.info("""
                    Status of order %d was updated (%s, %s)\s
                    """.formatted(order.getId(), oldStatus, newStatus));
            orderRepository.saveAndFlush(order);
        }
    }

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
