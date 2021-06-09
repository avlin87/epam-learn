package com.epam.liadov.repository;

import com.epam.liadov.domain.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * SupplierRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Integer> {

    /**
     * Method interact with database and store target object
     *
     * @param supplier target object
     * @param <S>      extending class
     * @return Optional<Supplier> container
     */
    @Override
    <S extends Supplier> S save(S supplier);

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Supplier> with found object on success else Optional.empty
     */
    @Override
    Optional<Supplier> findById(Integer primaryKey);

    /**
     * Method delete target object from database
     *
     * @param primaryKey target object
     * @return true in case of success else false
     */
    @Override
    void deleteById(Integer primaryKey);

    /**
     * Method of all Supplier objects in Database
     *
     * @return list
     */
    @Override
    List<Supplier> findAll();

    /**
     * Method for checking if entity exists in database
     *
     * @param primaryKey target object
     * @return true if found
     */
    @Override
    boolean existsById(Integer primaryKey);
}
