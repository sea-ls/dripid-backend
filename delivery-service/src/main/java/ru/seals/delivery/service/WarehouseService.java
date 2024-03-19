package ru.seals.delivery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.seals.delivery.dto.WarehouseSimpleViewDTO;
import ru.seals.delivery.model.Warehouse;

public interface WarehouseService {
    Page<WarehouseSimpleViewDTO> getAllWarehouse(Pageable pageable);
    Warehouse getById(Long id);
    void save(Warehouse warehouse);
    void deleteById(Long id);
}