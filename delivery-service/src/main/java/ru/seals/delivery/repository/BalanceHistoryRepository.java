package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.seals.delivery.model.delivery.BalanceHistory;

@Repository
public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {
}
