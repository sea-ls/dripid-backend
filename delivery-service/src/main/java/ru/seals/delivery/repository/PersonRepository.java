package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.seals.delivery.model.delivery.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("""
            from Person p
            join fetch p.saveAddresses sa
            where p.keycloakId = :kcId
            and sa.deleted = false
            """)
    Optional<Person> findPersonByKeycloakId(@Param("kcId") String kcId);
}
