package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.seals.delivery.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByTrackNumberExternal(String trackNumber);
    Order findByTrackNumberInternal(String trackNumber);
}
