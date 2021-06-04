package com.epam.liadov.repository.impl;

import com.epam.liadov.entity.Order;
import com.epam.liadov.repository.OrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
@Component
@RequiredArgsConstructor
@Profile("!local")
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManager entityManager;

    @Override
    public Optional<Order> save(Order order) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(order);
            transaction.commit();
            log.debug("Order sent to DataBase successfully: {}", order);
            return Optional.ofNullable(order);
        } catch (IllegalArgumentException | PersistenceException e) {
            transaction.rollback();
            log.error("Error during DB transaction ", e);
        }
        log.debug("Order was not sent to database: {}", order);
        return Optional.empty();
    }

    @Override
    public Optional<Order> update(@NonNull Order order) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (find(order.getOrderID()).isEmpty()) {
                return Optional.empty();
            }
            transaction.begin();
            order = entityManager.merge(order);
            transaction.commit();
            log.debug("object updated: {}", order);
            return Optional.ofNullable(order);
        } catch (IllegalArgumentException | ConstraintViolationException | RollbackException | TransactionRequiredException e) {
            transaction.rollback();
            log.error("Error during DB transaction ", e);
        }
        log.debug("object was not updated: {}", order);
        return Optional.empty();
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
    public boolean delete(@NonNull Order order) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.find(Order.class, order.getOrderID()));
            transaction.commit();
            log.debug("object removed successfully");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            transaction.rollback();
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