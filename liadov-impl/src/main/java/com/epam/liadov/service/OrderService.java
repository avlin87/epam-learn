package com.epam.liadov.service;

import com.epam.liadov.entity.Order;

import java.util.List;

/**
 * OrderService
 *
 * @author Aleksandr Liadov
 */
public interface OrderService {

    /**
     * Create order
     *
     * @param order entity
     * @return saved order entity
     */
    boolean save(Order order);

    /**
     * Update order
     *
     * @param order entity to update
     * @return - true if updated
     */
    boolean update(Order order);

    /**
     * Find Order by primaryKey
     *
     * @param primaryKey - id of object
     * @return found entity
     */
    Order find(int primaryKey);

    /**
     * Find Orders by customerId
     *
     * @param customerId - id of object
     * @return list of found entity
     */
    List<Order> findByCustomerId(int customerId);

    /**
     * Delete entity
     *
     * @param order to be deleted
     * @return - true if deleted
     */
    boolean delete(Order order);

    /**
     * Return all of Order entities
     *
     * @return List<> of entities
     */
    List<Order> getAll();

}