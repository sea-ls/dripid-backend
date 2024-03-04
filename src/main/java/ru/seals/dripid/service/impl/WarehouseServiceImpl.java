package ru.seals.dripid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.seals.dripid.dto.WarehouseSimpleViewDTO;
import ru.seals.dripid.model.Warehouse;
import ru.seals.dripid.repository.WarehouseRepository;
import ru.seals.dripid.service.WarehouseService;
import ru.seals.dripid.util.Convertor;

import static ru.seals.dripid.exception.WarehouseNotFoundException.warehouseNotFoundException;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository repository;
    private final Convertor convertor;

    @Override
    public Page<WarehouseSimpleViewDTO> getAllWarehouse(Pageable pageable) {
        Page<Warehouse> warehouses = repository.findAll(pageable);
        return convertor.mapEntityPageIntoDtoPage(warehouses, WarehouseSimpleViewDTO.class);
    }

    @Override
    public Warehouse getById(Long id) {
        return repository.findById(id)
                .orElseThrow(warehouseNotFoundException("Warehouse {0} not found!", id));
    }

    @Override
    public void save(Warehouse warehouse) {
        repository.save(warehouse);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
