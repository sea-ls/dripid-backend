package ru.seals.delivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.dto.WarehouseSaveDTO;
import ru.seals.delivery.dto.WarehouseSimpleViewDTO;
import ru.seals.delivery.model.delivery.Warehouse;
import ru.seals.delivery.service.WarehouseService;

@RestController
@RequestMapping(value = "/api/delivery-service/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final ModelMapper modelMapper;

    @GetMapping("/{page}")
    @Operation(description = "Получение страницы со складами")
    public Page<WarehouseSimpleViewDTO> getAllWarehouseSimpleViewDTO(@PathVariable int page, @RequestParam int size) {
        return warehouseService.getAllWarehouse(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение склада по ID")
    public Warehouse getWarehouseById(@PathVariable Long id) {
        return warehouseService.getById(id);
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Сохранение склада")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void saveWarehouse(
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "json") WarehouseSaveDTO warehouse) {
        warehouseService.save(file, null); //TODO поправить //TODO вернуть ResponseEntity.ok
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Изменение склада")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void updateWarehouse(
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "json") String warehouse) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        warehouseService.save(file, warehouse); //TODO вернуть ResponseEntity.ok
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Удаление склада по ID")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void deleteWarehouseById(@PathVariable Long id) {
        warehouseService.deleteById(id);
    }
}
