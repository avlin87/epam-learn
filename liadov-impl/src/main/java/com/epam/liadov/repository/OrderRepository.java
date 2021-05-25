package com.epam.liadov.repository;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;

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
public class OrderRepository {

    private final EntityManagerFactory entityManagerFactory;

    public OrderRepository(@NonNull EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Method interact with database and store target object
     *
     * @param order target object
     * @return boolean - true in case of success else false
     */
    public Optional<Order> save(Order order) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(order);
            transaction.commit();
            log.debug("{} sent to DataBase successfully", order);
            return Optional.ofNullable(order);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
        }
        log.debug("{} was not sent to database", order);
        return Optional.empty();
    }

    /**
     * Method interact with database and update target object
     *
     * @param order target object
     * @return - true in case of success else false
     */
    public boolean update(@NonNull Order order) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (find(order.getOrderID()).isEmpty()){
                return false;
            }
            entityManager.merge(order);
            transaction.commit();
            log.debug("object updated: {}", order);
            return true;
        } catch (IllegalArgumentException | ConstraintViolationException | RollbackException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Order order = entityManager.find(Order.class, primaryKey);
            log.debug("Found order = {}", order);
            return Optional.ofNullable(order);
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        } finally {
            entityManager.close();
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
    public boolean delete(@NonNull Order order) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        try {
            entityManager.remove(entityManager.find(Order.class, order.getOrderID()));
            transaction.commit();
            log.debug("object removed successfully");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("DataBase transaction error", e);
        } finally {
            entityManager.close();
        }
        log.trace("object was not removed");
        return false;
    }

    public List<Order> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Order> orderList = new ArrayList<>();
        try {
            orderList = entityManager.createQuery("select order from Order order", Order.class)
                    .getResultList();
            log.trace("Found orders = {}", orderList);
            return orderList;
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        } finally {
            entityManager.close();
        }
        log.debug("object not found");
        return orderList;
    }
}
