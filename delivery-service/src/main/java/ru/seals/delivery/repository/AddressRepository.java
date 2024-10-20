package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.seals.delivery.model.delivery.SaveAddress;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<SaveAddress, Long> {
    @Modifying
    @Query("update SaveAddress sa set sa.deleted = true where sa.id = :id")
    void softDelete(@Param("id") Long id);
    @Query("from SaveAddress sa where sa.id = :id and sa.deleted = false")
    Optional<SaveAddress> findExist(@Param("id") Long id);
}
