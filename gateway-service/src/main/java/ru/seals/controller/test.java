package ru.seals.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class test {
    @GetMapping("{id}")
    @Operation(description = "Получение юзера по ID")
    public void getById() {

    }
}
