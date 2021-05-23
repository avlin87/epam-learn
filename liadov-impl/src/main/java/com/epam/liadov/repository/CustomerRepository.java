package com.epam.liadov.repository;

import com.epam.liadov.entity.Customer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TransactionRequiredException;
import java.util.Optional;

/**
 * CustomerRepository - class for CRUD operations of Customer class. dataBase table = 'customer'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class CustomerRepository {

    private final EntityManagerFactory entityManagerFactory;

    public CustomerRepository(@NonNull EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Method interact with database and store target object
     *
     * @param customer target object
     * @return boolean - true in case of success else false
     */
    public Optional<Customer> save(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(customer);
            transaction.commit();
            log.debug("{} sent to DataBase successfully", customer);
            return Optional.ofNullable(customer);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
        }
        log.debug("{} was not sent to database", customer);
        return Optional.empty();
    }

    /**
     * Method interact with database and update target object
     *
     * @param customer target object
     * @return - true in case of success else false
     */
    public boolean update(@NonNull Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(customer);
            transaction.commit();
            log.debug("object updated: {}", customer);
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
        }
        log.debug("object was not updated: {}", customer);
        return false;
    }

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Customer> with found object on success else Optional.empty
     */
    public Optional<Customer> find(int primaryKey) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Customer customer = entityManager.find(Customer.class, primaryKey);
            log.debug("Found customer = {}", customer);
            return Optional.ofNullable(customer);
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
     * @param customer target object
     * @return true in case of success else false
     */
    public boolean delete(@NonNull Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        try {
            entityManager.remove(customer);
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
}
