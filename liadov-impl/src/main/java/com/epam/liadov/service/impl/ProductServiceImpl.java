package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Product;
import com.epam.liadov.repository.ProductRepository;
import com.epam.liadov.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductServiceImpl
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean save(Product product) {
        boolean saveResult = productRepository.save(product);
        log.trace("Product created: {}", saveResult);
        return saveResult;
    }

    @Override
    public boolean update(Product product) {
        return productRepository.update(product);
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
