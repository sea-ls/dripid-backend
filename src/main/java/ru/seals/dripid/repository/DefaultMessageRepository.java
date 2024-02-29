package ru.seals.dripid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.seals.dripid.model.DefaultMessage;

import java.util.List;

@Repository
public interface DefaultMessageRepository extends JpaRepository<DefaultMessage, Long> {
    List<DefaultMessage> findAllByMessageTypeName(String name);
}
