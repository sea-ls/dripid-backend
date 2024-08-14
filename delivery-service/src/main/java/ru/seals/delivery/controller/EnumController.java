package ru.seals.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.seals.delivery.model.enums.*;

@RestController
@RequestMapping(value = "/api/delivery-service/admin/enum")
@RequiredArgsConstructor
public class EnumController {
    @GetMapping("/message_status")
    @Operation(description = "Получение всех статусов сообщения. Пример - доставлено")
    public MessageStatus[] getAllMessageStatuses() {
        return MessageStatus.values();
    }

    @GetMapping("/order_status")
    @Operation(description = "Получение всех статусов заказа. Пример - обрабатывается")
    public OrderStatus[] getAllOrderStatuses() {
        return OrderStatus.values();
    }

    @GetMapping("/order_type")
    @Operation(description = "Получение всех типов заказа. Пример - покупка и доставка")
    public OrderType[] getAllOrderTypes() {
        return OrderType.values();
    }

    @GetMapping("/transaction_type")
    @Operation(description = "Получение всех типов транзакций")
    public TransactionType[] getAllTransactionTypes() {
        return TransactionType.values();
    }
}
