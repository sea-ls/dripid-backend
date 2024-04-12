package ru.seals.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.seals.delivery.dto.WarehouseSimpleViewDTO;
import ru.seals.delivery.model.Warehouse;
import ru.seals.delivery.service.WarehouseService;

@RestController
@RequestMapping(value = "/api/delivery-service/warehouses")
@RequiredArgsConstructor
@CrossOrigin
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping("/warehouses/{page}")
    @Operation(description = "Получение страницы со складами")
    public Page<WarehouseSimpleViewDTO> getAllWarehouseSimpleViewDTO(@PathVariable int page, @RequestParam int size) {
        return warehouseService.getAllWarehouse(PageRequest.of(page, size));
    }

    @GetMapping("/warehouse/{id}")
    @Operation(description = "Получение склада по ID")
    public Warehouse getWarehouseById(@PathVariable Long id) {
        return warehouseService.getById(id);
    }

    @PostMapping("/warehouse/save")
    @Operation(description = "Сохранение склада")
    public void saveWarehouse(@RequestBody Warehouse warehouse) {
        warehouseService.save(warehouse);
    }

    @DeleteMapping("/warehouse/delete/{id}")
    @Operation(description = "Удаление склада по ID")
    public void deleteWarehouseById(@PathVariable Long id) {
        warehouseService.deleteById(id);
    }
}
