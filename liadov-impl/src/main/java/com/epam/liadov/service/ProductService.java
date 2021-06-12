package com.epam.liadov.service;

import com.epam.liadov.domain.entity.Product;

import java.util.List;

/**
 * ProductService - interface for service operations with Product repository
 *
 * @author Aleksandr Liadov
 */
public interface ProductService {

    /**
     * Create product
     *
     * @param product entity
     * @return saved product entity
     */
    Product save(Product product);

    /**
     * Update product
     *
     * @param product entity to update
     * @return - true if updated
     */
    Product update(Product product);

    /**
     * Find Product by primaryKey
     *
     * @param productId - id of object
     * @return found entity
     */
    Product find(int productId);

    /**
     * Delete entity
     *
     * @param productId to be deleted
     * @return - true if deleted
     */
    boolean delete(int productId);

    /**
     * Return all of Product entities
     *
     * @return List<> of entities
     */
    List<Product> getAll();

}