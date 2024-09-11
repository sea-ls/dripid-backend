package ru.seals.delivery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.seals.delivery.model.delivery.Order;
import ru.seals.delivery.model.delivery.enums.OrderStatus;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByTrackNumberExternal(String trackNumber);
    Optional<Order> findByTrackNumberInternal(String trackNumber);

    @Query("""
            from Order o
            where o.person.keycloakId = :kc_id
            """)
    Page<Order> findAllByPersonKeycloakId(Pageable pageable, @Param("kc_id") String kc_id);

    @Query(value = """
            SELECT DISTINCT o
            FROM Order o
            JOIN Message m ON o.id = m.order.id
            """)
    List<Order> findAllWithMessage();
    @Query("""
            from Order o
            where o.orderStatus in :statuses
            """)
    Slice<Order> findAllByStatuses(@Param("statuses") Iterable<OrderStatus> statuses,
                                   Pageable pageable);

    //Один из возможных методов, которые пригодятся
    /*@Query(value = """
            SELECT DISTINCT o
            FROM Order o
            LEFT JOIN FETCH o.messages m
            WHERE m IS NOT NULL
            """)
    List<Order> findAllChatsForAdmin();*/
}
