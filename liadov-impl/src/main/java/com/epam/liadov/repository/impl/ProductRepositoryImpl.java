package com.epam.liadov.repository.impl;

import com.epam.liadov.entity.Product;
import com.epam.liadov.repository.ProductRepository;
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
 * ProductRepositoryImpl - class for CRUD operations of Product class. dataBase table = 'product'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!local")
public class ProductRepositoryImpl implements ProductRepository {

    private final EntityManager entityManager;

    @Override
    public Optional<Product> save(Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(product);
            transaction.commit();
            log.debug("{} sent to DataBase successfully", product);
            return Optional.ofNullable(product);
        } catch (IllegalArgumentException | PersistenceException e) {
            transaction.rollback();
            log.error("Error during save DB transaction ", e);
        }
        log.debug("{} was not sent to database", product);
        return Optional.empty();
    }

    @Override
    public Optional<Product> update(@NonNull Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (find(product.getProductId()).isEmpty()) {
                return Optional.empty();
            }
            transaction.begin();
            product = entityManager.merge(product);
            transaction.commit();
            log.debug("object updated: {}", product);
            return Optional.ofNullable(product);
        } catch (IllegalArgumentException | ConstraintViolationException | RollbackException | TransactionRequiredException e) {
            transaction.rollback();
            log.error("Error during DB transaction ", e);
        }
        log.debug("object was not updated: {}", product);
        return Optional.empty();
    }

    @Override
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

    @Override
    public boolean delete(@NonNull Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.find(Product.class, product.getProductId()));
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
