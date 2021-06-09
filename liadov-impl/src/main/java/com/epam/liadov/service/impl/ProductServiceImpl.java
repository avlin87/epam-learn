package com.epam.liadov.service.impl;

import com.epam.liadov.domain.Product;
import com.epam.liadov.repository.ProductRepository;
import com.epam.liadov.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
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
@Profile("!local")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        Optional<Product> optionalProduct = Optional.ofNullable(productRepository.save(product));
        boolean saveResult = optionalProduct.isPresent();
        log.trace("Product created: {}", saveResult);
        if (saveResult) {
            product = optionalProduct.get();
            return product;
        }
        return new Product();
    }

    @Override
    public Product update(Product product) {
        int productId = product.getProductId();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            optionalProduct = Optional.ofNullable(productRepository.save(product));
        }
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            log.trace("Product updated: {}", product);
            return product;
        }
        log.trace("Product was not updated");
        return new Product();
    }

    @Override
    public Product find(int primaryKey) {
        Optional<Product> optionalProduct = productRepository.findById(primaryKey);
        boolean findResult = optionalProduct.isPresent();
        log.trace("Product found: {}", findResult);
        if (findResult) {
            Product product = optionalProduct.get();
            return product;
        }
        return new Product();
    }

    @Override
    public boolean delete(int primaryKey) {
        boolean existsInDb = productRepository.existsById(primaryKey);
        log.trace("Entity for removal exist in BD: {}", existsInDb);
        if (existsInDb) {
            productRepository.deleteById(primaryKey);
            boolean entityExistAfterRemove = productRepository.existsById(primaryKey);
            log.trace("Entity removed: {}", entityExistAfterRemove);
            return !entityExistAfterRemove;
        }
        return false;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
