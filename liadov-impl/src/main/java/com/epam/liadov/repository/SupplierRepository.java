package com.epam.liadov.repository;

import com.epam.liadov.entity.Supplier;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SupplierRepository - class for CRUD operations of Supplier class. dataBase table = 'supplier'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class SupplierRepository {

    private final EntityManagerFactory entityManagerFactory;

    public SupplierRepository(@NonNull EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Method interact with database and store target object
     *
     * @param supplier target object
     * @return boolean - true in case of success else false
     */
    public Optional<Supplier> save(Supplier supplier) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(supplier);
            transaction.commit();
            log.debug("{} sent to DataBase successfully", supplier);
            return Optional.ofNullable(supplier);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
        }
        log.debug("{} was not sent to database", supplier);
        return Optional.empty();
    }

    /**
     * Method interact with database and update target object
     *
     * @param supplier target object
     * @return - true in case of success else false
     */
    public boolean update(@NonNull Supplier supplier) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (find(supplier.getSupplierId()).isEmpty()){
                return false;
            }
            entityManager.merge(supplier);
            transaction.commit();
            log.debug("object updated: {}", supplier);
            return true;
        } catch (IllegalArgumentException | ConstraintViolationException | RollbackException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
        }
        log.debug("object was not updated: {}", supplier);
        return false;
    }

    /**
     * Method finds objects in database by primaryKey
     *
     * @param primaryKey primaryKey of target object
     * @return Optional<Supplier> with found object on success else Optional.empty
     */
    public Optional<Supplier> find(int primaryKey) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Supplier supplier = entityManager.find(Supplier.class, primaryKey);
            log.debug("Found supplier = {}", supplier);
            return Optional.ofNullable(supplier);
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
     * @param supplier target object
     * @return true in case of success else false
     */
    public boolean delete(@NonNull Supplier supplier) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        try {
            entityManager.remove(entityManager.find(Supplier.class, supplier.getSupplierId()));
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

    public List<Supplier> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Supplier> supplierList = new ArrayList<>();
        try {
            supplierList = entityManager.createQuery("select supplier from Supplier supplier", Supplier.class)
                    .getResultList();
            log.trace("Found suppliers = {}", supplierList);
            return supplierList;
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        } finally {
            entityManager.close();
        }
        log.debug("object not found");
        return supplierList;
    }
}
