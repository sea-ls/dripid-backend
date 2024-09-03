package ru.seals.delivery.model.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.seals.delivery.model.common.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageType extends BaseEntity {
    private String name;
}
