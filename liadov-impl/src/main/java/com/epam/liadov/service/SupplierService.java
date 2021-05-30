package com.epam.liadov.service;

import com.epam.liadov.entity.Supplier;

import java.util.List;

/**
 * SupplierService
 *
 * @author Aleksandr Liadov
 */
public interface SupplierService {

    /**
     * Create supplier
     *
     * @param supplier entity
     * @return saved supplier entity
     */
    Supplier save(Supplier supplier);

    /**
     * Update supplier
     *
     * @param supplier entity to update
     * @return - true if updated
     */
    boolean update(Supplier supplier);

    /**
     * Find Supplier by primaryKey
     *
     * @param primaryKey - id of object
     * @return found entity
     */
    Supplier find(int primaryKey);

    /**
     * Delete entity
     *
     * @param supplier to be deleted
     * @return - true if deleted
     */
    boolean delete(Supplier supplier);

    /**
     * Return all of Supplier entities
     *
     * @return List<> of entities
     */
    List<Supplier> getAll();

}