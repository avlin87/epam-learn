package com.epam.liadov.repository;

import com.epam.liadov.entity.Order;
import com.epam.liadov.entity.Product;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

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
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
        } finally {
            entityManager.close();
        }
        log.debug("OrderProduct was not populated");
        return false;
    }
}
