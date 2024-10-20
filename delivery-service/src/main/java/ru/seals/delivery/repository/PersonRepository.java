package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.seals.delivery.model.delivery.Person;
import ru.seals.delivery.model.delivery.SaveAddress;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByKeycloakId(String kcId);
//    @Query("""
//            select sa
//            from Person p
//            join fetch p.saveAddresses sa
//            where p.keycloakId = :kcId
//            and sa.deleted = false
//            """)
//    List<SaveAddress> findPersonSaveAddresses(String kcId);
}
