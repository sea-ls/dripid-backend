package ru.seals.delivery.model.chat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.seals.delivery.model.common.BaseEntity;
import ru.seals.delivery.model.delivery.Order;
import ru.seals.delivery.model.delivery.Person;
import ru.seals.delivery.model.delivery.enums.MessageStatus;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Message extends BaseEntity {
    @ManyToOne
    private Order order;

    @ManyToOne
    private Person sender;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String content;
    private LocalDateTime createdAt;
}
