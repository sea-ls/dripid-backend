package ru.seals.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.seals.delivery.service.OrderService;

@RestController
@RequestMapping(value = "api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final OrderService orderService;

    @GetMapping("/tracking")
    public String getDeliveryHistory(@RequestParam String trackNumber) {
        return orderService.getDeliveryHistory(trackNumber);
    }
}
