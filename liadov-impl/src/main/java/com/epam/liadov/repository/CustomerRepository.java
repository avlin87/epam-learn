package com.epam.liadov.repository;

import com.epam.liadov.entity.Customer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CustomerRepository - class for CRUD operations of Customer class. dataBase table = 'customer'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Repository
public class CustomerRepository {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    /**
     * Method interact with database and store target object
     *
     * @param customer target object
     * @return boolean - true in case of success else false
     */
    @Transactional
    public boolean save(Customer customer) {
        try {
            entityManager.persist(customer);
            log.trace("customer sent to DB: {}", customer);
            return true;
        } catch (IllegalArgumentException | PersistenceException | DataIntegrityViolationException e) {
            log.error("Error during save DB transaction ", e);
        }
        log.debug("{} was not sent to database", customer);
        return false;
    }

    /**
     * Method interact with database and update target object
     *
     * @param customer target object
     * @return - true in case of success else false
     */
    @Transactional
    public boolean update(@NonNull Customer customer) {
        try {
            entityManager.merge(customer);
            log.debug("object updated: {}", customer);
            return true;
        } catch (IllegalArgumentException | PersistenceException | DataIntegrityViolationException e) {
            log.error("Error during update DB transaction ", e);
        }
        log.debug("Customer was not updated: {}", customer);
        return false;
    }

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Customer> with found object on success else Optional.empty
     */
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

    /**
     * Method delete target object from database
     *
     * @param customer target object
     * @return true in case of success else false
     */
    @Transactional
    public boolean delete(@NonNull Customer customer) {
        try {
            entityManager.remove(entityManager.find(Customer.class, customer.getCustomerId()));
            log.debug("object removed successfully");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("DataBase transaction error", e);
        }
        log.trace("object was not removed");
        return false;
    }

    /**
     * Method of all Customer objects in Database
     *
     * @return list
     */
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
