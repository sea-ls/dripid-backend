package ru.seals.delivery.model.chat;

import lombok.Data;

@Data
public class OrderChatDTO {
    private Long id;
    private String personFirstName;
    private String personLastName;
}
