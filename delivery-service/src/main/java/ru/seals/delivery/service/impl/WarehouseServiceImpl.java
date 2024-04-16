package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.dto.WarehouseSimpleViewDTO;
import ru.seals.delivery.model.Warehouse;
import ru.seals.delivery.model.enums.MinioBuckets;
import ru.seals.delivery.repository.WarehouseRepository;
import ru.seals.delivery.service.MinioService;
import ru.seals.delivery.service.WarehouseService;
import ru.seals.delivery.util.Convertor;

import java.util.UUID;

import static ru.seals.delivery.exception.WarehouseNotFoundException.warehouseNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository repository;
    private final Convertor convertor;
    private final ModelMapper modelMapper;
    private final MinioService minioService;

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
    public void save(MultipartFile multipartFile, Warehouse warehouse) {
        String fileName = UUID.randomUUID().toString();
        warehouse.setImage(fileName);
        repository.save(modelMapper.map(warehouse, Warehouse.class));
        minioService.saveImage(
                multipartFile,
                warehouse.getImage(),
                MinioBuckets.WAREHOUSE_BUCKET.getValue());
        log.info(String.format("Сохранение записи в таблице 'warehouse' с ID = %d выполнено успешно", warehouse.getId()));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}