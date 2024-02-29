package ru.seals.dripid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.seals.dripid.model.Warehouse;
import ru.seals.dripid.repository.WarehouseRepository;
import ru.seals.dripid.service.WarehouseService;

import static ru.seals.dripid.exception.WarehouseNotFoundException.warehouseNotFoundException;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository repository;

    @Override
    public Page<Warehouse> getAllWarehouse(Pageable pageable) {
        return repository.findAll(pageable);
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
