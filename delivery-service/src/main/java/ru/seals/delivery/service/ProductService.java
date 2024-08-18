package ru.seals.delivery.service;

import ru.seals.delivery.model.delivery.Product;

public interface ProductService {
    void save(Product product);
    void deleteById(Long id);
}
