package ru.seals.delivery.dto;

import lombok.Data;
import ru.seals.delivery.model.delivery.enums.OrderStatus;

@Data
public class OrderPreviewDTO {
    private Long id;
    private OrderStatus orderStatus;
    private String address;
    private String trackNumberExternal;
}
