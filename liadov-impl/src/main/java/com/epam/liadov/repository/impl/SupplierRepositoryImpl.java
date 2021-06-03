package com.epam.liadov.repository.impl;

import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.SupplierRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Profile;
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
@Profile("!local")
public class SupplierRepositoryImpl implements SupplierRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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
