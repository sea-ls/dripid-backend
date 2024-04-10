package ru.seals.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;
import ru.seals.delivery.model.DefaultMessage;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.model.chat.MessageType;
import ru.seals.delivery.service.AdminService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/delivery-service/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/default_message/type")
    @Operation(description = "Получение всех шаблонных сообщений по типу. Пример типа - приветствие")
    public List<DefaultMessage> getAllDefaultMessagesByType(@RequestBody MessageType type) {
        return adminService.getAllDefaultMessagesByType(type);
    }

    @GetMapping("/default_message/{id}")
    @Operation(description = "Получение шаблонного сообщения по ID")
    public DefaultMessage getDefaultMessageById(@PathVariable Long id) {
        return adminService.getDefaultMessagesById(id);
    }

    @DeleteMapping("/default_message/delete/{id}")
    @Operation(description = "Удаление шаблонного сообщения по ID")
    public void deleteDefaultMessageById(@PathVariable Long id) {
        adminService.deleteDefaultMessageById(id);
    }

   /* @PostMapping("/default_message/save")
    public void saveDefaultMessage(@RequestBody DefaultMessage defaultMessage, @RequestParam String type) {
        adminService.saveDefaultMessage(defaultMessage, type);
    }*/

    @PostMapping("/default_message/save")
    @Operation(description = "Сохранение шаблонного сообщения")
    public void saveDefaultMessage(@RequestBody DefaultMessage defaultMessage) {
        adminService.saveDefaultMessage(defaultMessage);
    }

    @GetMapping("/message_types")
    @Operation(description = "Получение всех типов шаблонных сообщений")
    public List<MessageType> getAllMessageTypes() {
        return adminService.getAllMessageTypes();
    }

    @DeleteMapping("/message_type/delete/{id}")
    @Operation(description = "Удаление типа по ID")
    public void deleteMessageTypeById(@PathVariable Long id) {
        adminService.deleteMessageTypeById(id);
    }

    @PostMapping("/message_type/save")
    @Operation(description = "Сохранение типа по ID")
    public void saveMessageType(@RequestBody MessageType messageType) {
        adminService.saveMessageType(messageType);
    }

    @GetMapping("/orders/{id}")
    @Operation(description = "Получение заказа по ID")
    public Order getOrderById(@PathVariable Long id) {
        return adminService.getOrderById(id);
    }

    @PostMapping("/orders/save")
    @Operation(description = "Сохранение заказа")
    public void saveOrder(@RequestBody Order order) {
        adminService.saveOrder(order);
    }

    @DeleteMapping("/orders/delete/{id}")
    @Operation(description = "Удаление заказа по ID")
    public void deleteOrder(@PathVariable Long id) {
        adminService.deleteOrderById(id);
    }

    @PostMapping("/order/delivery_history/update/{id}")
    @Operation(description = "Обновление последнего статуса доставки заказа по его ID")
    public void updateDeliveryHistory(@PathVariable Long id, @RequestBody HashMap<String, String> newStatus) {
        adminService.updateDeliveryHistory(id, newStatus);
    }

    @GetMapping("/tracking")
    @Operation(description = "Получение заказа по его внутреннему номеру")
    public Order getOrderByTrackInternalNumber(@RequestParam String trackNumber) {
        return adminService.getOrderByTrackIntervalNumber(trackNumber);
    }
}
