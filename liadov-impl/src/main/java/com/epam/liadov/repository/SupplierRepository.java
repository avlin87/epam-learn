package com.epam.liadov.repository;

import com.epam.liadov.entity.Supplier;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * SupplierRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
public interface SupplierRepository {

    /**
     * Method interact with database and store target object
     *
     * @param supplier target object
     * @return boolean - true in case of success else false
     */
    boolean save(Supplier supplier);

    /**
     * Method interact with database and update target object
     *
     * @param supplier target object
     * @return - true in case of success else false
     */
    boolean update(@NonNull Supplier supplier);

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Supplier> with found object on success else Optional.empty
     */
    Optional<Supplier> find(int primaryKey);

    /**
     * Method delete target object from database
     *
     * @param supplier target object
     * @return true in case of success else false
     */
    boolean delete(@NonNull Supplier supplier);

    /**
     * Method of all Supplier objects in Database
     *
     * @return list
     */
    List<Supplier> getAll();
}