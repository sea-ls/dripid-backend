package ru.seals.dripid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.seals.dripid.dto.WarehouseSimpleViewDTO;
import ru.seals.dripid.model.DefaultMessage;
import ru.seals.dripid.model.MessageType;
import ru.seals.dripid.model.Order;
import ru.seals.dripid.model.Warehouse;
import ru.seals.dripid.service.AdminService;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/default_message/type")
    public List<DefaultMessage> getAllDefaultMessagesByType(@RequestBody MessageType type) {
        return adminService.getAllDefaultMessagesByType(type);
    }

    @GetMapping("/default_message/{id}")
    public DefaultMessage getDefaultMessageById(@PathVariable Long id) {
        return adminService.getDefaultMessagesById(id);
    }

    @DeleteMapping("/default_message/delete/{id}")
    public void deleteDefaultMessageById(@PathVariable Long id) {
        adminService.deleteDefaultMessageById(id);
    }

   /* @PostMapping("/default_message/save")
    public void saveDefaultMessage(@RequestBody DefaultMessage defaultMessage, @RequestParam String type) {
        adminService.saveDefaultMessage(defaultMessage, type);
    }*/

    @PostMapping("/default_message/save")
    public void saveDefaultMessage(@RequestBody DefaultMessage defaultMessage) {
        adminService.saveDefaultMessage(defaultMessage);
    }

    @GetMapping("/message_types")
    public List<MessageType> getAllMessageTypes() {
        return adminService.getAllMessageTypes();
    }

    @DeleteMapping("/message_type/delete/{id}")
    public void deleteMessageTypeById(@PathVariable Long id) {
        adminService.deleteMessageTypeById(id);
    }

    @PostMapping("/message_type/save")
    public void saveMessageType(@RequestBody MessageType messageType) {
        adminService.saveMessageType(messageType);
    }

    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return adminService.getOrderById(id);
    }

    @PostMapping("/orders/save")
    public void saveOrder(@RequestBody Order order) {
        adminService.saveOrder(order);
    }

    @DeleteMapping("/orders/delete/{id}")
    public void saveOrder(@PathVariable Long id) {
        adminService.deleteOrderById(id);
    }

    @PostMapping("/order/delivery_history/{id}")
    public void updateDeliveryHistory(@PathVariable Long id, @RequestParam String status) {
        adminService.updateDeliveryHistory(id, status);
    }
}
