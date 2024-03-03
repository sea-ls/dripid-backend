package ru.seals.dripid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.seals.dripid.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
