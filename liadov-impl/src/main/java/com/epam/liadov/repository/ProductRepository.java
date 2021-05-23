package com.epam.liadov.repository;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Product;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TransactionRequiredException;
import java.util.Optional;

/**
 * ProductRepository - class for CRUD operations of Product class. dataBase table = 'product'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class ProductRepository {

    private final EntityManagerFactory entityManagerFactory;

    public ProductRepository(@NonNull EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Method interact with database and store target object
     *
     * @param product target object
     * @return boolean - true in case of success else false
     */
    public Optional<Product> save(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(product);
            transaction.commit();
            log.debug("{} sent to DataBase successfully", product);
            return Optional.ofNullable(product);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
        }
        log.debug("{} was not sent to database", product);
        return Optional.empty();
    }

    /**
     * Method interact with database and update target object
     *
     * @param product target object
     * @return - true in case of success else false
     */
    public boolean update(@NonNull Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(product);
            transaction.commit();
            log.debug("object updated: {}", product);
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Product product = entityManager.find(Product.class, primaryKey);
            log.debug("Found product = {}", product);
            return Optional.ofNullable(product);
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        } finally {
            entityManager.close();
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
    public boolean delete(@NonNull Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        try {
            entityManager.remove(entityManager.find(Product.class, product.getProductId()));
            transaction.commit();
            log.debug("object removed successfully");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("DataBase transaction error", e);
        } finally {
            entityManager.close();
        }
        log.trace("object was not removed");
        return false;
    }
}
