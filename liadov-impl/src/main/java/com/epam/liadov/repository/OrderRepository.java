package com.epam.liadov.repository;

import com.epam.liadov.entity.Order;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * OrderRepository - class for CRUD operations of Order class. dataBase table = 'order'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Method interact with database and store target object
     *
     * @param order target object
     * @return boolean - true in case of success else false
     */
    @Transactional
    public boolean save(Order order) {
        try {
            entityManager.persist(order);
            log.debug("Order sent to DataBase successfully: {}", order);
            return true;
        } catch (IllegalArgumentException | PersistenceException e) {
            log.error("Error during DB transaction ", e);
        }
        log.debug("Order was not sent to database: {}", order);
        return false;
    }

    /**
     * Method interact with database and update target object
     *
     * @param order target object
     * @return - true in case of success else false
     */
    @Transactional
    public boolean update(@NonNull Order order) {
        try {
            if (find(order.getOrderID()).isEmpty()) {
                return false;
            }
            entityManager.merge(order);
            log.debug("object updated: {}", order);
            return true;
        } catch (IllegalArgumentException | ConstraintViolationException | RollbackException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
        }
        log.debug("object was not updated: {}", order);
        return false;
    }

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Order> with found object on success else Optional.empty
     */
    public Optional<Order> find(int primaryKey) {
        try {
            Order order = entityManager.find(Order.class, primaryKey);
            log.debug("Found order = {}", order);
            return Optional.ofNullable(order);
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        }
        log.debug("object not found");
        return Optional.empty();
    }

    /**
     * Method delete target object from database
     *
     * @param order target object
     * @return true in case of success else false
     */
    @Transactional
    public boolean delete(@NonNull Order order) {
        try {
            entityManager.remove(entityManager.find(Order.class, order.getOrderID()));
            log.debug("object removed successfully");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("DataBase transaction error", e);
        }
        log.trace("object was not removed");
        return false;
    }

    /**
     * Method of all Order objects by CustomerId
     *
     * @return list
     */
    public List<Order> getOrdersByCustomerId(int customerId) {
        List<Order> orderList = new ArrayList<>();
        try {
            orderList = entityManager.createQuery("select order from Order order where order.customerId = :customerId", Order.class)
                    .setParameter("customerId", customerId)
                    .getResultList();
            log.trace("Found orders = {}", orderList);
            return orderList;
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        }
        log.debug("object not found");
        return orderList;
    }

    /**
     * Method of all Order objects in Database
     *
     * @return list
     */
    public List<Order> getAll() {
        List<Order> orderList = new ArrayList<>();
        try {
            orderList = entityManager.createQuery("select order from Order order", Order.class)
                    .getResultList();
            log.trace("Found orders = {}", orderList);
            return orderList;
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        }
        log.debug("object not found");
        return orderList;
    }
}
