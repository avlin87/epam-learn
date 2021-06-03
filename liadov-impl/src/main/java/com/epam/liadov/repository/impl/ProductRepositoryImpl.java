package com.epam.liadov.repository.impl;

import com.epam.liadov.entity.Product;
import com.epam.liadov.repository.ProductRepository;
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
 * ProductRepositoryImpl - class for CRUD operations of Product class. dataBase table = 'product'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Repository
@Profile("!local")
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
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

    @Override
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
