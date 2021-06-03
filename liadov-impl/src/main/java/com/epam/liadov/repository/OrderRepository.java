package com.epam.liadov.repository;

import com.epam.liadov.entity.Order;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * OrderRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
public interface OrderRepository {

    /**
     * Method interact with database and store target object
     *
     * @param order target object
     * @return boolean - true in case of success else false
     */
    boolean save(Order order);

    /**
     * Method interact with database and update target object
     *
     * @param order target object
     * @return - true in case of success else false
     */
    boolean update(@NonNull Order order);

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Order> with found object on success else Optional.empty
     */
    Optional<Order> find(int primaryKey);

    /**
     * Method delete target object from database
     *
     * @param order target object
     * @return true in case of success else false
     */
    boolean delete(@NonNull Order order);

    /**
     * Method of all Order objects by CustomerId
     *
     * @return list
     */
    List<Order> getOrdersByCustomerId(int customerId);

    /**
     * Method of all Order objects in Database
     *
     * @return list
     */
    List<Order> getAll();
}
