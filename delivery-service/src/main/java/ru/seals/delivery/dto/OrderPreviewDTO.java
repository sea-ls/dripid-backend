package ru.seals.delivery.dto;

import lombok.Data;
import ru.seals.delivery.model.enums.DeliveryStageType;
import ru.seals.delivery.model.enums.OrderStatus;

@Data
public class OrderPreviewDTO {
    private Long id;
    private OrderStatus orderStatus;
    private DeliveryStageType deliveryStageType;
    private String address;
    private String trackNumberExternal;
}
