package ru.seals.delivery.dto;

import lombok.Data;
import ru.seals.delivery.model.Person;
import ru.seals.delivery.model.Product;
import ru.seals.delivery.model.Warehouse;
import ru.seals.delivery.model.enums.DeliveryStageType;
import ru.seals.delivery.model.enums.OrderStatus;
import ru.seals.delivery.model.enums.OrderType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderSaveDTO {
    private OrderType orderType;
    private OrderStatus orderStatus;
    private LocalDateTime lastUpdate;
    private List<Product> products = new ArrayList<>();
    private Warehouse warehouse;
    private DeliveryStageType deliveryStageType;
    private Person person;
    private String deliveryHistory;
    private String trackNumberInternal;
    private String trackNumberExternal;
    private String address;
}
