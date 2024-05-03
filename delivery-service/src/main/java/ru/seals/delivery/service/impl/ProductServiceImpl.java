package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.seals.delivery.model.Product;
import ru.seals.delivery.repository.ProductRepository;
import ru.seals.delivery.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.saveAndFlush(product);
    }
}
