package ru.seals.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.seals.delivery.model.delivery.chat.MessageType;

@Repository
public interface MessageTypeRepository extends JpaRepository<MessageType, Long> {
    MessageType findByName(String name);
}
