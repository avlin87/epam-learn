package com.epam.liadov.repository;

import com.epam.liadov.entity.Product;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * ProductRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
public interface ProductRepository {

    /**
     * Method interact with database and store target object
     *
     * @param product target object
     * @return boolean - true in case of success else false
     */
    boolean save(Product product);

    /**
     * Method interact with database and update target object
     *
     * @param product target object
     * @return - true in case of success else false
     */
    boolean update(@NonNull Product product);

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Product> with found object on success else Optional.empty
     */
    Optional<Product> find(int primaryKey);

    /**
     * Method delete target object from database
     *
     * @param product target object
     * @return true in case of success else false
     */
    boolean delete(@NonNull Product product);

    /**
     * Method of all Product objects in Database
     *
     * @return list
     */
    List<Product> getAll();
}
