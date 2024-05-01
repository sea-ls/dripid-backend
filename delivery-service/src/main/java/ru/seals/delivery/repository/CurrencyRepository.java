package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.seals.delivery.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
