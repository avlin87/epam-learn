package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Product;
import com.epam.liadov.repository.ProductRepository;
import com.epam.liadov.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ProductServiceImpl - Service for operations with Product repository
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public boolean save(Product product) {
        Optional<Product> optionalProduct = productRepository.save(product);
        boolean saveResult = optionalProduct.isPresent();
        log.trace("Product created: {}", saveResult);
        return saveResult;
    }

    @Override
    public boolean update(Product product) {
        Optional<Product> optionalProduct = productRepository.update(product);
        return optionalProduct.isPresent();
    }

    @Override
    public Product find(int primaryKey) {
        return productRepository.find(primaryKey).orElse(new Product());
    }

    @Override
    public boolean delete(Product product) {
        return productRepository.delete(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.getAll();
    }
}
