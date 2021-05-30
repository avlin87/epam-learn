package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Product;
import com.epam.liadov.repository.ProductRepository;
import com.epam.liadov.service.ProductService;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

/**
 * ProductServiceImpl
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class ProductServiceImpl implements ProductService {

    private static final EntityManagerFactory entityPU = Persistence.createEntityManagerFactory("EntityPU");
    private final ProductRepository productRepository = new ProductRepository(entityPU);

    @Override
    public Product save(Product product) {
        Product createdProduct = new Product();
        Optional<Product> optionalProduct = productRepository.save(product);
        if (optionalProduct.isPresent()) {
            createdProduct = optionalProduct.get();
            log.trace("Product created successfully");
        } else {
            log.trace("Product was not created");
        }
        return createdProduct;
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
