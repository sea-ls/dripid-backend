package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.seals.delivery.model.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByKeycloakId(String kcId);
}
