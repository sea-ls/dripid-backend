package ru.seals.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.seals.delivery.dto.WarehouseSimpleViewDTO;
import ru.seals.delivery.model.Warehouse;
import ru.seals.delivery.service.WarehouseService;

@RestController
@RequestMapping(value = "delivery_service/api/v1/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping("/warehouses/{page}")
    public Page<WarehouseSimpleViewDTO> getAllWarehouseSimpleViewDTO(@PathVariable int page, @RequestParam int size) {
        return warehouseService.getAllWarehouse(PageRequest.of(page, size));
    }

    @GetMapping("/warehouse/{id}")
    public Warehouse getWarehouseById(@PathVariable Long id) {
        return warehouseService.getById(id);
    }

    @PostMapping("/warehouse/save")
    public void saveWarehouse(@RequestBody Warehouse warehouse) {
        warehouseService.save(warehouse);
    }

    @DeleteMapping("/warehouse/delete/{id}")
    public void deleteWarehouseById(@PathVariable Long id) {
        warehouseService.deleteById(id);
    }
}
