package ru.seals.dripid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.seals.dripid.model.DefaultMessage;
import ru.seals.dripid.model.MessageType;
import ru.seals.dripid.service.AdminService;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/default_message/type/{type}")
    public List<DefaultMessage> getAllDefaultMessagesByType(@PathVariable String type) {
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

    @PostMapping("/default_message/save")
    public void saveDefaultMessage(@RequestBody DefaultMessage defaultMessage, @RequestParam String type) {
        adminService.saveDefaultMessage(defaultMessage, type);
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
}
