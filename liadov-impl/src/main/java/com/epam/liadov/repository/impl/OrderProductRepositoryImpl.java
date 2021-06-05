package com.epam.liadov.repository.impl;

import com.epam.liadov.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderProductRepositoryImpl - class for dataBase operations table = 'orderProduct'
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!local")
public class OrderProductRepositoryImpl implements OrderProductRepository {

    private final EntityManager entityManager;

    @Override
    public boolean saveId(int orderId, List<Integer> productIds) {
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
        } catch (RuntimeException e) {
            transaction.rollback();
            log.error("Error during DB transaction ", e);
        }
        log.debug("OrderProduct was not populated");
        return false;
    }

    @Override
    public boolean updateId(int orderId, List<Integer> productIds) {
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
        } catch (RuntimeException | PSQLException e) {
            transaction.rollback();
            log.error("Error during DB transaction ", e);
        }
        return false;
    }

    private List<Integer> getCurrentProductList(int orderId, EntityManager entityManager) {
        List<Integer> integerList = new ArrayList<>();
        List rowsList = entityManager.createNativeQuery("select ordpro.productid from liadov.orderproduct ordpro where ordpro.orderId = :orderId")
                .setParameter("orderId", orderId)
                .getResultList();
        for (Object o : rowsList) {
            integerList.add(Integer.valueOf(o.toString()));
        }
        return integerList;
    }

    private void insertNewValues(int orderId, List<Integer> productIds, EntityManager entityManager, List<Integer> productsInDb) throws RuntimeException, PSQLException {
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

    private void deleteOldValues(int orderId, List<Integer> productIds, EntityManager entityManager, List<Integer> productsInDb) throws RuntimeException, PSQLException {
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