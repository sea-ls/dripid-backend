package ru.seals.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.seals.delivery.model.enums.*;


@RestController
@RequestMapping(value = "delivery_service/api/v1/admin/enum")
@RequiredArgsConstructor
public class EnumController {
    @GetMapping("/delivery_stage_type")
    public DeliveryStageType[] getAllDeliveryStageTypes() {
        return DeliveryStageType.values();
    }

    @GetMapping("/message_status")
    public MessageStatus[] getAllMessageStatuses() {
        return MessageStatus.values();
    }

    @GetMapping("/order_status")
    public OrderStatus[] getAllOrderStatuses() {
        return OrderStatus.values();
    }

    @GetMapping("/order_type")
    public OrderType[] getAllOrderTypes() {
        return OrderType.values();
    }

    @GetMapping("/transaction_type")
    public TransactionType[] getAllTransactionTypes() {
        return TransactionType.values();
    }
}
