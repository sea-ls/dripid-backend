package ru.seals.dripid.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.seals.dripid.model.Warehouse;

public interface WarehouseService {
    Page<Warehouse> getAllWarehouse(Pageable pageable);
    Warehouse getById(Long id);
    void save(Warehouse warehouse);
    void deleteById(Long id);
}
