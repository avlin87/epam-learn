package com.epam.liadov.repository.impl;

import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.SupplierRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
@Component
@RequiredArgsConstructor
@Profile("!local")
public class SupplierRepositoryImpl implements SupplierRepository {

    private final EntityManager entityManager;

    @Override
    public Optional<Supplier> save(Supplier supplier) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(supplier);
            transaction.commit();
            log.debug("Supplier sent to DataBase successfully: {}", supplier);
            return Optional.ofNullable(supplier);
        } catch (IllegalArgumentException | PersistenceException e) {
            transaction.rollback();
            log.error("Error during DB transaction ", e);
        }
        log.debug("Supplier was not sent to database: {}", supplier);
        return Optional.empty();
    }

    @Override
    public Optional<Supplier> update(@NonNull Supplier supplier) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (find(supplier.getSupplierId()).isEmpty()) {
                return Optional.empty();
            }
            transaction.begin();
            supplier = entityManager.merge(supplier);
            transaction.commit();
            log.debug("object updated: {}", supplier);
            return Optional.ofNullable(supplier);
        } catch (IllegalArgumentException | ConstraintViolationException | RollbackException | TransactionRequiredException e) {
            transaction.rollback();
            log.error("Error during DB transaction ", e);
        }
        log.debug("object was not updated: {}", supplier);
        return Optional.empty();
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
    public boolean delete(@NonNull Supplier supplier) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.find(Supplier.class, supplier.getSupplierId()));
            transaction.commit();
            log.debug("object removed successfully");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            transaction.rollback();
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
