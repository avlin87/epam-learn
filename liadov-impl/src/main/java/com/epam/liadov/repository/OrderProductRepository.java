package com.epam.liadov.repository;

import com.epam.liadov.entity.Order;
import com.epam.liadov.entity.Product;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderProductRepository - class for dataBase operations table = 'orderProduct'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class OrderProductRepository {
    private final EntityManagerFactory entityManagerFactory;

    public OrderProductRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Method populate orderProduct table in database
     *
     * @param order   target oder
     * @param product target product
     * @return boolean - true in case of success else false
     */
    public boolean save(@NonNull Order order, @NonNull Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Query nativeQuery = entityManager.createNativeQuery("insert into liadov.orderproduct (orderid, productid) values (?,?)")
                    .setParameter(1, order.getOrderID())
                    .setParameter(2, product.getProductId());
            nativeQuery.executeUpdate();
            transaction.commit();
            log.debug("OrderProduct table populated");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException | ConstraintViolationException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
        }
        log.debug("OrderProduct was not populated");
        return false;
    }

    /**
     * Method populate orderProduct table in database
     *
     * @param orderId    target orderId
     * @param productIds target productId
     * @return - true in case of success
     */
    public boolean saveId(int orderId, List<Integer> productIds) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Query insertQuery = entityManager.createNativeQuery("insert into liadov.orderproduct (orderid, productid) values (?,?)")
                    .setParameter(1, orderId);
            for (int productId : productIds) {
                insertQuery.setParameter(2, productId);
                insertQuery.executeUpdate();
            }
            transaction.commit();
            log.debug("OrderProduct table populated");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException | ConstraintViolationException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
        }
        log.debug("OrderProduct was not populated");
        return false;
    }

    /**
     * Method update orderProduct table
     *
     * @param orderId    target orderId
     * @param productIds List of target productId
     * @return true in case of success
     */
    public boolean updateId(int orderId, List<Integer> productIds) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            List<Integer> productsInDb = getCurrentProductList(orderId, entityManager);
            if ((productIds.containsAll(productsInDb)) && (productsInDb.containsAll(productIds))) {
                log.debug("OrderProduct table up to date");
                return true;
            } else {
                insertNewValues(orderId, productIds, entityManager, productsInDb);
                deleteOldValues(orderId, productIds, entityManager, productsInDb);
            }
            transaction.commit();
            log.debug("OrderProduct table populated");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException | ConstraintViolationException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
        }
        return false;
    }

    private List<Integer> getCurrentProductList(int orderId, EntityManager entityManager) {
        List<Integer> integerList = new ArrayList<>();
        List rowsList = entityManager.createNativeQuery("select ordpro.productid from liadov.orderproduct ordpro where ordpro.orderId = :orderId")
                .setParameter("orderId", orderId)
                .getResultList();
        for (int i = 0; i < rowsList.size(); i++) {
            integerList.add(Integer.valueOf(rowsList.get(i).toString()));
        }
        return integerList;
    }

    private void insertNewValues(int orderId, List<Integer> productIds, EntityManager entityManager, List<Integer> productsInDb) {
        Query insertQuery = entityManager.createNativeQuery("insert into liadov.orderproduct (orderid, productid) values (?,?)")
                .setParameter(1, orderId);
        for (int productId : productIds) {
            if (!productsInDb.contains(productId)) {
                log.trace("inset: IntegerList = {}, productId = {}", productsInDb, productId);
                insertQuery.setParameter(2, productId);
                insertQuery.executeUpdate();
            }
        }
    }

    private void deleteOldValues(int orderId, List<Integer> productIds, EntityManager entityManager, List<Integer> productsInDb) {
        Query deleteQuery = entityManager.createNativeQuery("delete from liadov.orderproduct ordpro where ordpro.orderid= ? and productid = ?")
                .setParameter(1, orderId);
        for (int productId : productsInDb) {
            if (!productIds.contains(productId)) {
                log.trace("delete: productIds = {}, productId = {}", productsInDb, productId);
                deleteQuery.setParameter(2, productId);
                deleteQuery.executeUpdate();
            }
        }
    }
}
