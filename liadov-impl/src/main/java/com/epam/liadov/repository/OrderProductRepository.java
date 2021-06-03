package com.epam.liadov.repository;

import java.util.List;

/**
 * OrderProductRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
public interface OrderProductRepository {

    /**
     * Method populate orderProduct table in database
     *
     * @param orderId    target orderId
     * @param productIds target productId
     * @return - true in case of success
     */
    boolean saveId(int orderId, List<Integer> productIds);

    /**
     * Method update orderProduct table
     *
     * @param orderId    target orderId
     * @param productIds List of target productId
     * @return true in case of success
     */
    boolean updateId(int orderId, List<Integer> productIds);

}
