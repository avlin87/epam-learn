package com.epam.liadov.repository;

import com.epam.liadov.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * OrderRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * Method interact with database and store target object
     *
     * @param order target object
     * @param <S>   extending class
     * @return Optional<Order> container
     */
    @Override
    <S extends Order> S save(S order);

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Order> with found object on success else Optional.empty
     */
    @Override
    Optional<Order> findById(Integer primaryKey);

    /**
     * Method delete target object from database
     *
     * @param primaryKey target object
     * @return true in case of success else false
     */
    @Override
    void deleteById(Integer primaryKey);

    /**
     * Method of all Order objects by CustomerId
     *
     * @return list
     */
    List<Order> findAllByCustomerId(Integer customerId);

    /**
     * Method of all Order objects in Database
     *
     * @return list
     */
    @Override
    List<Order> findAll();

    /**
     * Method for checking if entity exists in database
     *
     * @param primaryKey target object
     * @return true if found
     */
    @Override
    boolean existsById(Integer primaryKey);
}
