package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.seals.delivery.model.DefaultMessage;
import ru.seals.delivery.model.MessageType;

import java.util.List;

@Repository
public interface DefaultMessageRepository extends JpaRepository<DefaultMessage, Long> {
    List<DefaultMessage> findAllByMessageType(MessageType type);
}
