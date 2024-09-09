package ru.seals.delivery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.dto.WarehouseSimpleViewDTO;
import ru.seals.delivery.model.delivery.Warehouse;

public interface WarehouseService {
    Page<WarehouseSimpleViewDTO> getAllWarehouse(Pageable pageable);
    Warehouse getById(Long id);
    void save(MultipartFile multipartFile, String warehouse);
    void deleteById(Long id);
}
