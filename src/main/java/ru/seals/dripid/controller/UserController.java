package ru.seals.dripid.controller;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.seals.dripid.service.OrderService;

@RestController
@RequestMapping(value = "api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final OrderService orderService;

    @GetMapping("/default_message/{trackNumber}")
    public String getDeliveryHistory(@PathVariable String trackNumber) {
        return orderService.getDeliveryHistory(trackNumber);
    }
}
