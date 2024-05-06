package ru.seals.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.seals.delivery.dto.DefaultMessageSaveDTO;
import ru.seals.delivery.dto.MessageTypeSaveDTO;
import ru.seals.delivery.dto.OrderSaveDTO;
import ru.seals.delivery.dto.ProductSaveDTO;
import ru.seals.delivery.model.DefaultMessage;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.model.Product;
import ru.seals.delivery.model.chat.MessageType;
import ru.seals.delivery.service.AdminService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/delivery-service/admin")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final AdminService adminService;
    private final ModelMapper modelMapper;

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

    @PostMapping("/default_message/save")
    @Operation(description = "Сохранение шаблонного сообщения")
    public void saveDefaultMessage(@RequestBody DefaultMessageSaveDTO defaultMessage) {
        adminService.saveDefaultMessage(modelMapper.map(defaultMessage, DefaultMessage.class));
    }

    @PutMapping("/default_message/update")
    @Operation(description = "Изменение шаблонного сообщения")
    public void updateDefaultMessage(@RequestBody DefaultMessage defaultMessage) {
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
    public void saveMessageType(@RequestBody MessageTypeSaveDTO messageType) {
        adminService.saveMessageType(modelMapper.map(messageType, MessageType.class));
    }

    @PutMapping("/message_type/update")
    @Operation(description = "Изменение типа по ID")
    public void updateMessageType(@RequestBody MessageType messageType) {
        adminService.saveMessageType(messageType);
    }

    @PostMapping("/order/save")
    @Operation(description = "Сохранение заказа")
    public void saveOrder(@RequestBody OrderSaveDTO order) {
        adminService.saveOrder(order);
    }

    @PutMapping("/order/update")
    @Operation(description = "Изменение заказа")
    public void updateOrder(@RequestBody Order order) {
        adminService.updateOrder(order);
    }

    @DeleteMapping("/order/delete/{id}")
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

    @PostMapping("/product/save")
    @Operation(description = "Сохранение продукта")
    public void saveProduct(@RequestBody ProductSaveDTO product, Long orderId) {
        adminService.saveProduct(modelMapper.map(product, Product.class), orderId);
    }

    @DeleteMapping("/product/delete/{id}")
    @Operation(description = "Удаление продукта по ID")
    public void deleteProductById(@PathVariable Long id) {
        adminService.deleteProductById(id);
    }
}
