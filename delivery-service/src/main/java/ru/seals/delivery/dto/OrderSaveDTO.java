package ru.seals.delivery.dto;


import lombok.Data;
import ru.seals.delivery.model.delivery.SaveAddress;
import ru.seals.delivery.model.delivery.enums.OrderType;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderSaveDTO {
    private OrderType orderType;
    private List<ProductSaveDTO> products = new ArrayList<>();
    private Long warehouseId;
    private String trackNumberExternal;
    private Long addressId;
}
