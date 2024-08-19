package ru.seals.delivery.service;

import ru.seals.delivery.model.delivery.SaveAddress;

public interface AddressService {
    SaveAddress getAddressById(Long id);
    void saveNewAddress(SaveAddress saveAddress);
    void deleteAddress(Long id);
}
