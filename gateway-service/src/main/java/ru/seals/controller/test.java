package ru.seals.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class test {
    @GetMapping("/1")
    @Operation(description = "Получение юзера по ID")
    public String getById() {
        return "a";
    }
}
