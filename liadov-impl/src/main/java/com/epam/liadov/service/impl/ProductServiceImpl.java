package com.epam.liadov.service.impl;

import com.epam.liadov.domain.entity.Product;
import com.epam.liadov.exception.BadRequestException;
import com.epam.liadov.exception.NoContentException;
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
        throw new BadRequestException("Product was not saved");
    }

    @Override
    public Product update(Product product) {
        int productId = product.getProductId();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            optionalProduct = Optional.ofNullable(productRepository.save(product));
            if (optionalProduct.isPresent()) {
                product = optionalProduct.get();
                log.trace("Product updated: {}", product);
                return product;
            }
        }
        log.trace("Product was not updated");
        throw new NoContentException("Product does not exist");
    }

    @Override
    public Product find(int productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        boolean findResult = optionalProduct.isPresent();
        log.trace("Product found: {}", findResult);
        if (findResult) {
            Product product = optionalProduct.get();
            return product;
        }
        throw new NoContentException("Product does not exist");
    }

    @Override
    public boolean delete(int productId) {
        boolean existsInDb = productRepository.existsById(productId);
        log.trace("Entity for removal exist in BD: {}", existsInDb);
        if (existsInDb) {
            productRepository.deleteById(productId);
            boolean entityExistAfterRemove = productRepository.existsById(productId);
            log.trace("Entity removed: {}", entityExistAfterRemove);
            return !entityExistAfterRemove;
        }
        throw new NoContentException("Product does not exist");
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
