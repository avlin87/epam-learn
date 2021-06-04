package com.epam.liadov.repository.impl;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.repository.CustomerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CustomerRepositoryImpl - class for CRUD operations of Customer class. dataBase table = 'customer'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!local")
public class CustomerRepositoryImpl implements CustomerRepository {

    private final EntityManager entityManager;

    @Override
    public Optional<Customer> save(Customer customer) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(customer);
            transaction.commit();
            log.trace("customer sent to DB: {}", customer);
            return Optional.ofNullable(customer);
        } catch (IllegalArgumentException | PersistenceException e) {
            transaction.rollback();
            log.error("Error during save DB transaction ", e);
        }
        log.debug("{} was not sent to database", customer);
        return Optional.empty();
    }

    @Override
    public Optional<Customer> update(@NonNull Customer customer) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            customer = entityManager.merge(customer);
            transaction.commit();
            log.debug("object updated: {}", customer);
            return Optional.of(customer);
        } catch (IllegalArgumentException | PersistenceException e) {
            transaction.rollback();
            log.error("Error during update DB transaction ", e);
        }
        log.debug("Customer was not updated: {}", customer);
        return Optional.empty();
    }

    @Override
    public Optional<Customer> find(int primaryKey) {
        try {
            Customer customer = entityManager.find(Customer.class, primaryKey);
            log.debug("Found customer = {}", customer);
            return Optional.ofNullable(customer);
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(@NonNull Customer customer) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.find(Customer.class, customer.getCustomerId()));
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
    public List<Customer> getAll() {
        List<Customer> customerList = new ArrayList<>();
        try {
            customerList = entityManager.createQuery("select customer from Customer customer", Customer.class)
                    .getResultList();
            log.trace("Found customers = {}", customerList);
            return customerList;
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        }
        log.debug("object not found");
        return customerList;
    }
}
