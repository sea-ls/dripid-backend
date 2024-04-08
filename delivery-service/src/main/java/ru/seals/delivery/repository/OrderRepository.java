package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.seals.delivery.model.Order;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByTrackNumberExternal(String trackNumber);
    Order findByTrackNumberInternal(String trackNumber);

    @Query(value = """
            SELECT DISTINCT o
            FROM Order o
            JOIN Message m ON o.id = m.order.id
            """)
    List<Order> findAllWithMessage();

    //Один из возможных методов, которые пригодятся
    /*@Query(value = """
            SELECT DISTINCT o
            FROM Order o
            LEFT JOIN FETCH o.messages m
            WHERE m IS NOT NULL
            """)
    List<Order> findAllChatsForAdmin();*/
}