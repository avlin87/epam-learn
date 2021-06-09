package com.epam.liadov.repository;

import com.epam.liadov.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ProductRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    /**
     * Method interact with database and store target object
     *
     * @param product target object
     * @param <S>     extending class
     * @return Optional<Product> container
     */
    @Override
    <S extends Product> S save(S product);

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Product> with found object on success else Optional.empty
     */
    @Override
    Optional<Product> findById(Integer primaryKey);

    /**
     * Method delete target object from database
     *
     * @param primaryKey target object
     * @return true in case of success else false
     */
    @Override
    void deleteById(Integer primaryKey);

    /**
     * Method of all Product objects in Database
     *
     * @return list
     */
    @Override
    List<Product> findAll();

    /**
     * Method for checking if entity exists in database
     *
     * @param primaryKey target object
     * @return true if found
     */
    @Override
    boolean existsById(Integer primaryKey);
}
