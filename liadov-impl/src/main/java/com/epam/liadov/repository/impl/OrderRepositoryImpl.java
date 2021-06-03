package com.epam.liadov.repository.impl;

import com.epam.liadov.entity.Order;
import com.epam.liadov.repository.OrderRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * OrderRepositoryImpl - class for CRUD operations of Order class. dataBase table = 'order'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Repository
@Profile("!local")
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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