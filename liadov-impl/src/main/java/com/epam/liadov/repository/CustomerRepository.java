package com.epam.liadov.repository;

import com.epam.liadov.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * CustomerRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    /**
     * Method interact with database and store target object
     *
     * @param customer target object
     * @param <S>      extending class
     * @return Optional<Customer> container
     */
    @Override
    <S extends Customer> S save(S customer);

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Customer> with found object on success else Optional.empty
     */
    @Override
    Optional<Customer> findById(Integer primaryKey);

    /**
     * Method delete target object from database
     *
     * @param primaryKey target object
     */
    @Override
    void deleteById(Integer primaryKey);

    /**
     * Method of all Customer objects in Database
     *
     * @return list
     */
    @Override
    List<Customer> findAll();

    /**
     * Method for checking if entity exists in database
     *
     * @param primaryKey target object
     * @return true if found
     */
    @Override
    boolean existsById(Integer primaryKey);
}
