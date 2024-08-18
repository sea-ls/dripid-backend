package ru.seals.delivery.model.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.seals.delivery.model.common.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification extends BaseEntity {
    private String senderId;
    private String recipientId;
    private String content;
}
