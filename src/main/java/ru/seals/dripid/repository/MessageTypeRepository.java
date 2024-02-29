package ru.seals.dripid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.seals.dripid.model.MessageType;

@Repository
public interface MessageTypeRepository extends JpaRepository<MessageType, Long> {
    MessageType findByName(String name);
}
