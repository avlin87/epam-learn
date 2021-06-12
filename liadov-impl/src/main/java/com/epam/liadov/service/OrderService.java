package com.epam.liadov.service;

import com.epam.liadov.domain.entity.Order;

import java.util.List;

/**
 * OrderService - interface for service operations with Order repository
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
    Order save(Order order);

    /**
     * Update order
     *
     * @param order entity to update
     * @return - true if updated
     */
    Order update(Order order);

    /**
     * Find Order by primaryKey
     *
     * @param orderId - id of object
     * @return found entity
     */
    Order find(int orderId);

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
     * @param orderId to be deleted
     * @return - true if deleted
     */
    boolean delete(int orderId);

    /**
     * Return all of Order entities
     *
     * @return List<> of entities
     */
    List<Order> getAll();

}