package com.epam.liadov.service;

import com.epam.liadov.domain.Customer;

import java.util.List;

/**
 * CustomerService - interface for service operations with Customer repository
 *
 * @author Aleksandr Liadov
 */
public interface CustomerService {

    /**
     * Create customer
     *
     * @param customer entity
     * @return saved customer entity
     */
    Customer save(Customer customer);

    /**
     * Update customer
     *
     * @param customer entity to update
     * @return - true if updated
     */
    Customer update(Customer customer);

    /**
     * Find Customer by primaryKey
     *
     * @param primaryKey - id of entity
     * @return found entity
     */
    Customer find(int primaryKey);

    /**
     * Delete entity
     *
     * @param primaryKey to be deleted
     * @return - true if deleted
     */
    boolean delete(int primaryKey);

    /**
     * Return all of Customer entities
     *
     * @return List<> of entities
     */
    List<Customer> getAll();

}