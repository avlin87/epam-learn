package com.epam.liadov.repository;

import com.epam.liadov.entity.Customer;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * CustomerRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
public interface CustomerRepository {

    /**
     * Method interact with database and store target object
     *
     * @param customer target object
     * @return Optional<Customer> container
     */
    Optional<Customer> save(Customer customer);

    /**
     * Method interact with database and update target object
     *
     * @param customer target object
     * @return Optional<Customer> container
     */
    Optional<Customer> update(@NonNull Customer customer);

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Customer> with found object on success else Optional.empty
     */
    Optional<Customer> find(int primaryKey);

    /**
     * Method delete target object from database
     *
     * @param customer target object
     * @return true in case of success else false
     */
    boolean delete(@NonNull Customer customer);

    /**
     * Method of all Customer objects in Database
     *
     * @return list
     */
    List<Customer> getAll();
}
