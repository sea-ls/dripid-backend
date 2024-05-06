package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.seals.delivery.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
