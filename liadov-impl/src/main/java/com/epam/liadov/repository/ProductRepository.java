package com.epam.liadov.repository;

import com.epam.liadov.entity.Product;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ProductRepository - class for CRUD operations of Product class. dataBase table = 'product'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * Method interact with database and store target object
     *
     * @param product target object
     * @return boolean - true in case of success else false
     */
    @Transactional
    public boolean save(Product product) {
        try {
            entityManager.persist(product);
            log.debug("{} sent to DataBase successfully", product);
            return true;
        } catch (IllegalArgumentException | PersistenceException | DataIntegrityViolationException e) {
            log.error("Error during save DB transaction ", e);
        }
        log.debug("{} was not sent to database", product);
        return false;
    }

    /**
     * Method interact with database and update target object
     *
     * @param product target object
     * @return - true in case of success else false
     */
    @Transactional
    public boolean update(@NonNull Product product) {
        try {
            if (find(product.getProductId()).isEmpty()) {
                return false;
            }
            entityManager.merge(product);
            log.debug("object updated: {}", product);
            return true;
        } catch (IllegalArgumentException | ConstraintViolationException | RollbackException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
        }
        log.debug("object was not updated: {}", product);
        return false;
    }

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Product> with found object on success else Optional.empty
     */
    public Optional<Product> find(int primaryKey) {
        try {
            Product product = entityManager.find(Product.class, primaryKey);
            log.debug("Found product = {}", product);
            return Optional.ofNullable(product);
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        }
        log.debug("object not found");
        return Optional.empty();
    }

    /**
     * Method delete target object from database
     *
     * @param product target object
     * @return true in case of success else false
     */
    @Transactional
    public boolean delete(@NonNull Product product) {
        try {
            entityManager.remove(entityManager.find(Product.class, product.getProductId()));
            log.debug("object removed successfully");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("DataBase transaction error", e);
        }
        log.trace("object was not removed");
        return false;
    }

    /**
     * Method of all Product objects in Database
     *
     * @return list
     */
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        try {
            productList = entityManager.createQuery("select product from Product product", Product.class)
                    .getResultList();
            log.trace("Found products = {}", productList);
            return productList;
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        }
        log.debug("object not found");
        return productList;
    }
}
