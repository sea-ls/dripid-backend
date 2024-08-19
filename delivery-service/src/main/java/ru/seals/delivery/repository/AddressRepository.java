package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.seals.delivery.model.delivery.SaveAddress;

public interface AddressRepository extends JpaRepository<SaveAddress, Long> {
}
