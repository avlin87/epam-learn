package com.epam.liadov.repository;

import com.epam.liadov.entity.Supplier;
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
 * SupplierRepository - class for CRUD operations of Supplier class. dataBase table = 'supplier'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Repository
public class SupplierRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Method interact with database and store target object
     *
     * @param supplier target object
     * @return boolean - true in case of success else false
     */
    @Transactional
    public boolean save(Supplier supplier) {
        try {
            entityManager.persist(supplier);
            log.debug("Supplier sent to DataBase successfully: {}", supplier);
            return true;
        } catch (IllegalArgumentException | PersistenceException | DataIntegrityViolationException e) {
            log.error("Error during DB transaction ", e);
        }
        log.debug("Supplier was not sent to database: {}", supplier);
        return false;
    }

    /**
     * Method interact with database and update target object
     *
     * @param supplier target object
     * @return - true in case of success else false
     */
    @Transactional
    public boolean update(@NonNull Supplier supplier) {
        try {
            if (find(supplier.getSupplierId()).isEmpty()) {
                return false;
            }
            entityManager.merge(supplier);
            log.debug("object updated: {}", supplier);
            return true;
        } catch (IllegalArgumentException | ConstraintViolationException | RollbackException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
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
        try {
            Supplier supplier = entityManager.find(Supplier.class, primaryKey);
            log.debug("Found supplier = {}", supplier);
            return Optional.ofNullable(supplier);
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
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
    @Transactional
    public boolean delete(@NonNull Supplier supplier) {
        try {
            entityManager.remove(entityManager.find(Supplier.class, supplier.getSupplierId()));
            log.debug("object removed successfully");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("DataBase transaction error", e);
        }
        log.trace("object was not removed");
        return false;
    }

    /**
     * Method of all Supplier objects in Database
     *
     * @return list
     */
    public List<Supplier> getAll() {
        List<Supplier> supplierList = new ArrayList<>();
        try {
            supplierList = entityManager.createQuery("select supplier from Supplier supplier", Supplier.class)
                    .getResultList();
            log.trace("Found suppliers = {}", supplierList);
            return supplierList;
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        }
        log.debug("object not found");
        return supplierList;
    }
}
