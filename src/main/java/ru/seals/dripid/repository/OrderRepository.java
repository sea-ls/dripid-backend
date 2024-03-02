package ru.seals.dripid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.seals.dripid.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
