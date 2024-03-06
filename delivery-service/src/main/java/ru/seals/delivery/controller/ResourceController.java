package ru.seals.delivery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @GetMapping("/resource")
    public String getHello() {
        return "Hello world";
    }
}
