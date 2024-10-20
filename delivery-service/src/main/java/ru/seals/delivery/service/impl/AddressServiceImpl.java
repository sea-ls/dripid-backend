package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.seals.delivery.model.delivery.SaveAddress;
import ru.seals.delivery.repository.AddressRepository;
import ru.seals.delivery.service.AddressService;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository repository;

    @Override
    public SaveAddress getAddressById(Long id) {
        return repository.findById(id)
                .orElseThrow(); //TODO добавить ошибку
    }

    @Override
    public void saveNewAddress(SaveAddress saveAddress) {
        repository.save(saveAddress);
    }

    @Override
    @Transactional
    public void deleteAddress(Long id) {
        repository.softDelete(id);
    }
}
