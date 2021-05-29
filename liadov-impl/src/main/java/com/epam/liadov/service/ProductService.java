package com.epam.liadov.service;

import com.epam.liadov.entity.Product;

import java.util.List;

/**
 * ProductService
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
    boolean update(Product product);

    /**
     * Find Product by primaryKey
     *
     * @param primaryKey - id of object
     * @return found entity
     */
    Product find(int primaryKey);

    /**
     * Delete entity
     *
     * @param product to be deleted
     * @return - true if deleted
     */
    boolean delete(Product product);

    /**
     * Return all of Product entities
     *
     * @return List<> of entities
     */
    List<Product> getAll();

}